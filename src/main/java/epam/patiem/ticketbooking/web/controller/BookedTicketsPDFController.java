package epam.patiem.ticketbooking.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import epam.patiem.ticketbooking.facade.impl.BookingFacadeImpl;
import epam.patiem.ticketbooking.model.Ticket;
import epam.patiem.ticketbooking.model.User;
import epam.patiem.ticketbooking.utils.PDFUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping(value = "/tickets/user", consumes = APPLICATION_PDF_VALUE, produces = APPLICATION_PDF_VALUE)
public class BookedTicketsPDFController {

    private static final Logger log = LoggerFactory.getLogger(BookedTicketsPDFController.class);

    private final BookingFacadeImpl bookingFacade;

    private final PDFUtils pdfUtils;

    public BookedTicketsPDFController(BookingFacadeImpl bookingFacade, PDFUtils pdfUtils) {
        this.bookingFacade = bookingFacade;
        this.pdfUtils = pdfUtils;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getBookedTicketsByUserPDF(@PathVariable String userId,
                                                            @RequestParam int pageSize,
                                                            @RequestParam int pageNum) {
        log.info("Showing the tickets by user with id: {}", userId);

        User userById = getUserById(userId);
        List<Ticket> bookedTickets = getBookedTickets(userId, pageSize, pageNum, userById);

        log.info("The tickets successfully found");

        return createResponseEntityWithPDFDocument(bookedTickets);
    }

    private User getUserById(String userId) {
        User userById = bookingFacade.getUserById(userId);
        if (isNull(userById)) {
            log.info("Can not to find a user by id: {}", userId);
            throw new RuntimeException("Can not to find a user by id: " + userId);
        }
        return userById;
    }

    private List<Ticket> getBookedTickets(String userId, int pageSize, int pageNum, User userById) {
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(userById, pageSize, pageNum);
        if (bookedTickets.isEmpty()) {
            log.info("Can not to find the tickets by user with id: {}", userId);
            throw new RuntimeException("Can not to find the tickets by user with id: " + userId);
        }
        return bookedTickets;
    }

    private ResponseEntity<Object> createResponseEntityWithPDFDocument(List<Ticket> bookedTickets) {
        createPDFDocument(bookedTickets);
        InputStreamResource pdfDocument = pdfUtils.getPDFDocument();
        pdfUtils.deletePDFDocument();
        return new ResponseEntity<>(pdfDocument, HttpStatus.OK);
    }

    private void createPDFDocument(List<Ticket> bookedTickets) {
        Path path = Paths.get("Booked Tickets.pdf");
        pdfUtils.setTickets(bookedTickets);
        pdfUtils.setPath(path);
        pdfUtils.createPDFFileOfBookedTicketsByUser();
    }

    private boolean isNull(Object object) {
        return object == null;
    }
}
