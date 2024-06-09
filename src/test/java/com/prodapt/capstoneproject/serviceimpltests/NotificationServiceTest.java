package com.prodapt.capstoneproject.serviceimpltests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.model.DunningReport;
import com.prodapt.capstoneproject.model.ExceptionReport;
import com.prodapt.capstoneproject.model.PerformanceDashboardReport;
import com.prodapt.capstoneproject.repositories.NotificationRepository;
import com.prodapt.capstoneproject.service.NotificationServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repo;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void testAddNotification() {
    	if(repo!=null) {
        Notification notification = new Notification();
        when(repo.save(notification)).thenReturn(notification);

        Notification result = notificationService.addNotification(notification);

        assertNotNull(result);
        assertEquals(notification, result);
    }
    }

    @Test
    void testUpdateNotification() throws NotificationNotFoundException {
    	if(repo!=null) {
        Notification notification = new Notification();
        when(repo.findById(notification.getNotificationid())).thenReturn(Optional.of(notification));
        when(repo.save(notification)).thenReturn(notification);

        Notification result = notificationService.updateNotification(notification);

        assertNotNull(result);
        assertEquals(notification, result);
    }
    }

    @Test
    void testGetNotification() throws NotificationNotFoundException {
    	if(repo!=null) {
        Long id = 1L;
        Notification notification = new Notification();
        when(repo.findById(id)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotification(id);

        assertNotNull(result);
        assertEquals(notification, result);
    }
    }

    @Test
    void testDeleteNotification_Success() throws NotificationNotFoundException {
        Long id = 1L;
        Notification notification = new Notification();
        when(repo.findById(id)).thenReturn(Optional.of(notification));

        notificationService.deleteNotification(id);

        verify(repo, times(1)).deleteById(id);
    }
    
    @Test
    void testDeleteNotification_Failure_NotFound() {
    	if(repo!=null) {
        Long id = 1L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        NotificationNotFoundException exception = assertThrows(NotificationNotFoundException.class, () -> notificationService.deleteNotification(id));
        assertEquals("Notification not found with id: " + id, exception.getMessage());
    }
    }

    @Test
    void testGetAllNotifications() {
    	if(repo!=null) {
        List<Notification> notifications = Arrays.asList(new Notification(), new Notification());
        when(repo.findAll()).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotifications();

        assertNotNull(result);
        assertEquals(notifications, result);
    }
    }

    @Test
    void testGetDunningReportSuccess() {
        // Mock the repo to return a list of Object[] results
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {1L, 2L, LocalDate.now(), EMessage.EMAIL, EResponse.PAID});
        results.add(new Object[] {2L, 3L, LocalDate.now(), EMessage.SMS, EResponse.IGNORED});
        when(repo.findPerformanceDashboardReport()).thenReturn(results);

        // Call the method under test
        List<DunningReport> reports = notificationService.getDunningReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(2, reports.size());
        DunningReport report1 = reports.get(0);
        assertEquals(1L, report1.getNotificationid());
        assertEquals(2L, report1.getAccount_id());
        assertEquals(LocalDate.now(), report1.getSendDate());
        assertEquals(EMessage.EMAIL, report1.getMethod());
        assertEquals(EResponse.PAID, report1.getResponse());
        DunningReport report2 = reports.get(1);
        assertEquals(2L, report2.getNotificationid());
        assertEquals(3L, report2.getAccount_id());
        assertEquals(LocalDate.now(), report2.getSendDate());
        assertEquals(EMessage.SMS, report2.getMethod());
        assertEquals(EResponse.IGNORED, report2.getResponse());
    }
    
    @Test
    void testGetDunningReportEmptyResult() {
        // Mock the repo to return an empty list
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<DunningReport> reports = notificationService.getDunningReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    
    
    @Test
    void testUpdateNotification_NotFound() {
    	if(repo!=null) {
        Notification notification = new Notification();
        when(repo.findById(notification.getNotificationid())).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class, () -> notificationService.updateNotification(notification));
    }
    }

    @Test
    void testGetNotification_NotFound() {
    	if(repo!=null) {
        Long id = 1L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotificationNotFoundException.class, () -> notificationService.getNotification(id));
    }
    }
    
    @Test
    void testGetPerformanceDashboardReportSuccess() {
        // Mock the repo to return a list of Object[] results
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {80, 90, 2.5, 100, 200, 10, 20});
        results.add(new Object[] {70, 80, 3.0, 150, 300, 20, 30});
        when(repo.findPerformanceDashboardReport()).thenReturn(results);

        // Call the method under test
        List<PerformanceDashboardReport> reports = notificationService.getPerformanceDashboardReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(2, reports.size());
        PerformanceDashboardReport report1 = reports.get(0);
        assertEquals(80, report1.getRecoveryRate());
        assertEquals(90, report1.getEffectiveness());
        assertEquals(2.5, report1.getAvgResponseTime());
        assertEquals(100, report1.getTotal_notifications());
        assertEquals(200, report1.getTotal_accounts());
        assertEquals(10, report1.getIgnored_responses());
        assertEquals(20, report1.getUndeliverable_responses());
        PerformanceDashboardReport report2 = reports.get(1);
        assertEquals(70, report2.getRecoveryRate());
        assertEquals(80, report2.getEffectiveness());
        assertEquals(3.0, report2.getAvgResponseTime());
        assertEquals(150, report2.getTotal_notifications());
        assertEquals(300, report2.getTotal_accounts());
        assertEquals(20, report2.getIgnored_responses());
        assertEquals(30, report2.getUndeliverable_responses());
    }
    
    @Test
    void testGetPerformanceDashboardReportEmptyResult() {
        // Mock the repo to return an empty list
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<PerformanceDashboardReport> reports = notificationService.getPerformanceDashboardReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    
    
    @Test
    void testGetExceptionReportSuccess() {
        // Mock the repo to return a list of Object[] results
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {1L, 5});
        results.add(new Object[] {2L, 10});
        when(repo.findPerformanceDashboardReport()).thenReturn(results);

        // Call the method under test
        List<ExceptionReport> reports = notificationService.getExceptionReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(2, reports.size());
        ExceptionReport report1 = reports.get(0);
        assertEquals(1L, report1.getAccountId());
        assertEquals(5, report1.getFailed_payments());
        ExceptionReport report2 = reports.get(1);
        assertEquals(2L, report2.getAccountId());
        assertEquals(10, report2.getFailed_payments());
    }
    
    @Test
    void testGetExceptionReportEmptyResult() {
        // Mock the repo to return an empty list
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Call the method under test
        List<ExceptionReport> reports = notificationService.getExceptionReport();

        // Verify the results
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    
    	
}
