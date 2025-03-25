package epam.patiem.ticketbooking.service.impl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import epam.patiem.ticketbooking.model.Event;
import epam.patiem.ticketbooking.repository.EventRepository;
import epam.patiem.ticketbooking.service.EventService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class EventServiceImpl implements EventService {

    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getEventById(String eventId) {
        log.info("Finding an event by id: {}", eventId);
        try {
            Event event = eventRepository.findById(new ObjectId(String.valueOf(eventId)))
                    .orElseThrow(() -> new RuntimeException("Can not to find an event by id: " + eventId));
            log.info("Event with id {} successfully found ", eventId);
            return event;
        } catch (RuntimeException e) {
            log.warn("Can not to find an event by id: " + eventId);
            return null;
        }
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.warn("Finding all events by title {} with page size {} and number of page {}",
                title, pageSize, pageNum);
        try {
            if (title.isEmpty()) {
                log.warn("The title can not be empty");
                return new ArrayList<>();
            }
            Page<Event> eventsByTitle = eventRepository.getAllByTitle(PageRequest.of(pageNum - 1, pageSize), title);
            if (!eventsByTitle.hasContent()) {
                throw new RuntimeException("Can not to find a list of events by title: " + title);
            }
            log.info("All events successfully found by title {} with page size {} and number of page {}",
                    title, pageSize, pageNum);
            return eventsByTitle.getContent();
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of events by title {}", title, e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("Finding all events for day {} with page size {} and number of page {}",
                day, pageSize, pageNum);
        try {
            if (day == null) {
                log.warn("The day can not be null");
                return new ArrayList<>();
            }
            Page<Event> eventsByTitle = eventRepository.getAllByDate(PageRequest.of(pageNum - 1, pageSize), day);
            if (!eventsByTitle.hasContent()) {
                throw new RuntimeException("Can not to find a list of events for day: " + day);
            }
            log.info("All events successfully found for day {} with page size {} and number of page {}",
                    day, pageSize, pageNum);

            return eventsByTitle.getContent();
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of events for day {}", day, e);
            return new ArrayList<>();
        }
    }

    @Override
    public Event createEvent(Event event) {
        log.info("Start creating an event: {}", event);
        try {
            if (isEventNull(event)) {
                log.warn("The event can not be null");
                return null;
            }
            if (eventExistsByTitleAndDay(event)) {
                log.warn("These title and day are already exists for one event");
                return null;
            }
            event = eventRepository.save(event);
            log.info("Successfully creation of the event: {}", event);
            return event;
        } catch (RuntimeException e) {
            log.warn("Can not to create an event: {}", event, e);
            return null;
        }
    }

    private boolean eventExistsByTitleAndDay(Event event) {
        return eventRepository.existsByTitleAndDate(event.getTitle(), event.getDate());
    }



    private boolean isEventNull(Event event) {
        return event == null;
    }

    @Override
    public Event updateEvent(Event event) {
        log.info("Start updating an event: {}", event);
        try {
            if (isEventNull(event)) {
                throw new RuntimeException("The event can not be null");
            }
            if (!eventExistsById(event)) {
                throw new RuntimeException("This event does not exist");
            }
            if (eventExistsByTitleAndDay(event)) {
                throw new RuntimeException("These title and day are already exists for one event");
            }
            event = eventRepository.save(event);
            log.info("Successfully updating of the event: {}", event);
            return event;
        } catch (RuntimeException e) {
            log.warn("Can not to update an event: {}", event, e);
            return null;
        }
    }

    private boolean eventExistsById(Event event) {
        return eventRepository.existsById(event.getId());
    }

    @Override
    public boolean deleteEvent(String eventId) {
        log.info("Start deleting an event with id: {}", eventId);
        try {
            eventRepository.deleteById(new ObjectId(String.valueOf(eventId)));
            log.info("Successfully deletion of the event with id: {}", eventId);
            return true;
        } catch (RuntimeException e) {
            log.warn("Can not to delete an event with id: {}", eventId, e);
            return false;
        }
    }
}