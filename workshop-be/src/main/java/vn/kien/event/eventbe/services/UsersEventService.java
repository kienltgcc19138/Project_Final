package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kien.event.eventbe.base.BaseService;
import vn.kien.event.eventbe.base.BaseSort;
import vn.kien.event.eventbe.converter.UsersEventConverter;
import vn.kien.event.eventbe.entity.*;
import vn.kien.event.eventbe.exception.ErrorCode;
import vn.kien.event.eventbe.exception.ServiceException;
import vn.kien.event.eventbe.repository.IEventRepository;
import vn.kien.event.eventbe.repository.IUsersRepository;
import vn.kien.event.eventbe.repository.IUsersEventRepository;
import vn.kien.event.eventbe.request.CreateAddScoreRequest;
import vn.kien.event.eventbe.request.RegisterEventRequest;
import vn.kien.event.eventbe.request.SearchUsersEventRequest;
import vn.kien.event.eventbe.response.NotificationResponse;
import vn.kien.event.eventbe.response.UsersEventDetailResponse;
import vn.kien.event.eventbe.response.UsersEventResponse;
import vn.kien.event.eventbe.utils.PageableUtils;
import vn.kien.event.eventbe.utils.StringUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsersEventService extends BaseService {
    private final IUsersEventRepository userEventRepository;
    private final IEventRepository eventRepository;
    private final IUsersRepository userRepository;
    private final NotificationService notificationService;
    private final EntityManager entityManager;
    private final MailService mailService;

    @Transactional(rollbackFor = Exception.class)
    public UsersEventDetailResponse addScoreUserEvent(String usersId, Long eventId, Float score) {
        UsersEvent usersEvent = userEventRepository.findById(new UsersEventId(usersId, eventId)).orElse(null);
        if (usersEvent == null) {
            throw new ServiceException(ErrorCode.USER_NOT_REGISTER_EVENT);
        }
        usersEvent.setScore(score);
        userEventRepository.save(usersEvent);
        return UsersEventConverter.convertToUsersDetailResponse(usersEvent);
    }

    public void exportEvent(Long eventId, HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Users Event");

            String[] columns = {"No", "Event Name", "Time Start", "Time End", "Student Id", "Student Name", "Student Email", "Course", "Major", "Phone", "Score"};

            // Create a Row
            Row headerRow = sheet.createRow(0);
            // Create cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            // Create Other rows and cells

            List<UsersEvent> usersEvents = userEventRepository.findAllByEvent_EventIdAndIsJoin(eventId, true);
            for (int i = 0; i < usersEvents.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(i + 1);

                cell = row.createCell(1);
                cell.setCellValue(usersEvents.get(i).getEvent().getName());


                cell = row.createCell(2);
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(usersEvents.get(i).getEvent().getTimeStart()));


                cell = row.createCell(3);
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(usersEvents.get(i).getEvent().getTimeEnd()));


                cell = row.createCell(4);
                cell.setCellValue(usersEvents.get(i).getUsers().getUsersId());

                cell = row.createCell(5);
                cell.setCellValue(usersEvents.get(i).getUsers().getFullName());


                cell = row.createCell(6);
                cell.setCellValue(usersEvents.get(i).getUsers().getEmail());


                Course course = usersEvents.get(i).getUsers().getCourse();

                cell = row.createCell(7);
                cell.setCellValue(course != null ? course.getName() : "");

                cell = row.createCell(8);
                cell.setCellValue(usersEvents.get(i).getUsers().getMajor() != null ? usersEvents.get(i).getUsers().getMajor().getName() : "");

                cell = row.createCell(9);
                cell.setCellValue(usersEvents.get(i).getUsers().getPhone());

                cell = row.createCell(10);
                cell.setCellValue(usersEvents.get(i).getScore());
            }

            String headerValue = "attachment; filename=file-event-detail-" + eventId + ".xlsx";
            response.setHeader(headerKey, headerValue);

            ServletOutputStream outputStream = null;
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Set<UsersEventDetailResponse> addScoreUserEvent(List<CreateAddScoreRequest> requests) {
        List<UsersEvent> results = new ArrayList<>();
        for (CreateAddScoreRequest request : requests) {
            UsersEvent usersEvent = userEventRepository.findById(new UsersEventId(request.getUsersId(), request.getEventId())).orElse(null);
            if (usersEvent == null) {
                throw new ServiceException(ErrorCode.USER_NOT_REGISTER_EVENT);
            }
            usersEvent.setIsJoin(request.getIsJoin());
            usersEvent.setScore(request.getScore());
            userEventRepository.save(usersEvent);
            results.add(usersEvent);

            Notification notification = new Notification();
            notification.setUsers(usersEvent.getUsers());
            notification.setRead(false);
            notification.setEventId(usersEvent.getEvent().getEventId());
            notification.setDescription("Register event " + usersEvent.getEvent().getName() + " successfully");
            notification.setCreatedAt(new Date());
            NotificationResponse notificationResponse = notificationService.createNotification(notification);
            notificationService.sendNotification(usersEvent.getUsers().getUsersId(), notificationResponse);
        }
        return UsersEventConverter.convertToUsersDetailResponses(new LinkedHashSet<>(results));
    }

    public Page<UsersEventDetailResponse> searchUsersEvent(SearchUsersEventRequest request) {
        if (request.getPageSize() == null || request.getPageSize() <= 0) {
            request.setPageSize(10);
        }
        Pageable pageable = PageableUtils.convertPageableAndSort(request.getPageNumber(), request.getPageSize(), request.getSort());
        Specification<Event> specification = Specification.where(null);
        if (request.getEventId() != null) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("event").get("eventId"), request.getEventId()));
        }
        if (!StringUtils.isBlankOrNull(request.getUsersId())) {
            specification = specification.and((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("users").get("usersId"), request.getUsersId()));
        }
        Page<UsersEvent> usersEvents = userEventRepository.findAll(specification, pageable);
        List<UsersEvent> usersEventList = usersEvents.getContent();
        List<UsersEventId> usersEventIds = usersEventList.stream().map(UsersEvent::getId).collect(Collectors.toList());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsersEvent> criteriaQuery = criteriaBuilder.createQuery(UsersEvent.class);
        Root<UsersEvent> root = criteriaQuery.from(UsersEvent.class);
        criteriaQuery.select(root).where(root.get("id").in(usersEventIds));
        if (request.getSort() != null && request.getSort().size() > 0) {
            List<Order> orders = new ArrayList<>();
            for (BaseSort sortRequest : request.getSort()) {
                if (sortRequest.getAsc()) {
                    orders.add(criteriaBuilder.asc(root.get(sortRequest.getKey())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortRequest.getKey())));
                }
            }
            criteriaQuery.orderBy(orders);
        }
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("users-event-joined");
        TypedQuery<UsersEvent> query = entityManager.createQuery(criteriaQuery);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        usersEvents = new PageImpl<>(query.getResultList(), pageable, usersEvents.getTotalElements());

        return new PageImpl<>(new ArrayList<>(UsersEventConverter.convertToUsersDetailResponses(new LinkedHashSet<>(usersEvents.getContent()))), pageable, usersEvents.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public UsersEventResponse registerEvent(Long id, RegisterEventRequest request) {

        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            throw new ServiceException(ErrorCode.EVENT_NOT_FOUND);
        }
        UsersEvent usersEventCheck = userEventRepository.findById(new UsersEventId(request.getUsersId(), id)).orElse(null);
        if (usersEventCheck != null) {
            throw new ServiceException(ErrorCode.USER_REGISTERED_EVENT);
        }

        Users users = userRepository.findById(request.getUsersId()).orElse(null);
        if (users == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        UsersEvent usersEvent = new UsersEvent();
        usersEvent.setUsers(users);
        usersEvent.setEvent(event);
        usersEvent.setCreatedAt(new Date());
        usersEvent.setId(new UsersEventId(request.getUsersId(), id));
        usersEvent.setIsJoin(false);
        UsersEvent usersEventCreate = userEventRepository.save(usersEvent);

        //send mail register sucessfully
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    mailService.sendMailRegisterEvent(users, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("Send mail" + users.getEmail() + " register event successfully");
            }
        });
        return UsersEventConverter.convertToUserEventResponse(usersEventCreate);
    }

    public Float getScoreUsers(String usersId) {
        List<UsersEvent> usersEvents = userEventRepository.findAllByUsers_UsersId(usersId);
        Float score = Float.valueOf(0);
        for (UsersEvent usersEvent : usersEvents) {
            if (usersEvent.getIsJoin() && usersEvent.getScore() != null) {
                score += usersEvent.getScore();
            }
        }
        return score;
    }
}
