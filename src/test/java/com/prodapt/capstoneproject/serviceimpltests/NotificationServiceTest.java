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

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Long notificationId = 1L;
        when(repo.existsById(notificationId)).thenReturn(true);
        
        notificationService.deleteNotification(notificationId);
        
        verify(repo, times(1)).deleteById(notificationId);
    }

    @Test
    void testDeleteNotification_NotFound() {
        Long notificationId = 1L;
        when(repo.existsById(notificationId)).thenReturn(false);
        
        assertThrows(NotificationNotFoundException.class, () -> {
            notificationService.deleteNotification(notificationId);
        });
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
        // Arrange
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{1L, 101L, Date.valueOf(LocalDate.now()), "EMAIL", "PAID"});
        when(repo.findPerformanceDashboardReport()).thenReturn(mockResults);

        // Act
        List<DunningReport> reports = notificationService.getDunningReport();

        // Assert
        assertNotNull(reports);
        assertEquals(1, reports.size());
        DunningReport report = reports.get(0);
        assertEquals(1L, report.getNotificationid());
        assertEquals(101L, report.getAccount_id());
        assertEquals(LocalDate.now(), report.getSendDate());
        assertEquals(EMessage.EMAIL, report.getMethod());
        assertEquals(EResponse.PAID, report.getResponse());
    }
    
    @Test
    void testGetDunningReportFailure() {
        // Arrange
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Act
        List<DunningReport> reports = notificationService.getDunningReport();

        // Assert
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
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
        // Arrange
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{BigDecimal.valueOf(0.75), BigDecimal.valueOf(0.85), BigDecimal.valueOf(1.2), 10L, 5L, 2L, 1L});
        when(repo.findPerformanceDashboardReport()).thenReturn(mockResults);

        // Act
        List<PerformanceDashboardReport> reports = notificationService.getPerformanceDashboardReport();

        // Assert
        assertNotNull(reports);
        assertEquals(1, reports.size());
        PerformanceDashboardReport report = reports.get(0);
        assertEquals(BigDecimal.valueOf(0.75), report.getRecoveryRate());
        assertEquals(BigDecimal.valueOf(0.85), report.getEffectiveness());
        assertEquals(BigDecimal.valueOf(1.2), report.getAvgResponseTime());
        assertEquals(10L, report.getTotal_notifications());
        assertEquals(5L, report.getTotal_accounts());
        assertEquals(2L, report.getIgnored_responses());
        assertEquals(1L, report.getUndeliverable_responses());
    }

    @Test
    void testGetPerformanceDashboardReportFailure() {
        // Arrange
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Act
        List<PerformanceDashboardReport> reports = notificationService.getPerformanceDashboardReport();

        // Assert
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
    }

    @Test
    void testGetExceptionReportSuccess() {
        // Arrange
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{102L, 3L});
        when(repo.findPerformanceDashboardReport()).thenReturn(mockResults);

        // Act
        List<ExceptionReport> reports = notificationService.getExceptionReport();

        // Assert
        assertNotNull(reports);
        assertEquals(1, reports.size());
        ExceptionReport report = reports.get(0);
        assertEquals(102L, report.getAccountId());
        assertEquals(3L, report.getFailed_payments());
    }

    @Test
    void testGetExceptionReportFailure() {
        // Arrange
        when(repo.findPerformanceDashboardReport()).thenReturn(new ArrayList<>());

        // Act
        List<ExceptionReport> reports = notificationService.getExceptionReport();

        // Assert
        assertNotNull(reports);
        assertTrue(reports.isEmpty());
    }
}
    
    	

