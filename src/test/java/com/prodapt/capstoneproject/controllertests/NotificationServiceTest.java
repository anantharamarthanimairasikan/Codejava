package com.prodapt.capstoneproject.controllertests;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prodapt.capstoneproject.controller.NotificationController;
import com.prodapt.capstoneproject.entities.EMessage;
import com.prodapt.capstoneproject.entities.EResponse;
import com.prodapt.capstoneproject.entities.Notification;
import com.prodapt.capstoneproject.exceptions.NotificationNotFoundException;
import com.prodapt.capstoneproject.service.NotificationService;

public class NotificationServiceTest {
	@InjectMocks
    private NotificationController notificationController;
 
    @Mock
    private NotificationService notificationService;
 
    private Notification notification;
    private List<Notification> notificationList;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notification = new Notification();
        notification.setNotificationid(1L);
        notification.setSendDate(LocalDate.now());
        notification.setMethod(EMessage.EMAIL);
        notification.setResponse(EResponse.PAID);
 
        notificationList = Arrays.asList(notification);
    }
 
    @Test
    public void testAddNotification_Success() {
        when(notificationService.addNotification(notification)).thenReturn(notification);
        ResponseEntity<Notification> response = notificationController.addNotification(notification);
 
        verify(notificationService).addNotification(notification);
        assert(response.getStatusCode() == HttpStatus.CREATED);
        assert(response.getBody().equals(notification));
    }
 
    @Test
    public void testGetAllNotifications_Success() {
        when(notificationService.getAllNotifications()).thenReturn(notificationList);
        ResponseEntity<List<Notification>> response = notificationController.getAllNotifications();
 
        verify(notificationService).getAllNotifications();
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(notificationList));
    }
 
    @Test
    public void testGetNotification_Success() throws NotificationNotFoundException {
        Long id = 1L;
        when(notificationService.getNotification(id)).thenReturn(notification);
        ResponseEntity<Notification> response = (ResponseEntity<Notification>) notificationController.getNotification(id);
 
        verify(notificationService).getNotification(id);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(notification));
    }
 
    @Test
    public void testUpdateNotification_Success() throws NotificationNotFoundException {
        when(notificationService.updateNotification(notification)).thenReturn(notification);
        ResponseEntity<Notification> response = (ResponseEntity<Notification>) notificationController.updateNotification(notification);
 
        verify(notificationService).updateNotification(notification);
        assert(response.getStatusCode() == HttpStatus.OK);
        assert(response.getBody().equals(notification));
    }
 
    @Test
    public void testDeleteNotification_Success() throws NotificationNotFoundException {
        Long id = 1L;
        doNothing().when(notificationService).deleteNotification(id);
        ResponseEntity<Void> response = (ResponseEntity<Void>) notificationController.deleteNotification(id);
 
        verify(notificationService).deleteNotification(id);
        assert(response.getStatusCode() == HttpStatus.NO_CONTENT);
    }
 
    @Test
    public void testGetNotification_NotFound() throws NotificationNotFoundException {
        Long id = 2L;
        when(notificationService.getNotification(id)).thenThrow(NotificationNotFoundException.class);
        ResponseEntity<Notification> response = (ResponseEntity<Notification>) notificationController.getNotification(id);
 
        verify(notificationService).getNotification(id);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
 
    @Test
    public void testUpdateNotification_NotFound() throws NotificationNotFoundException {
        when(notificationService.updateNotification(notification)).thenThrow(NotificationNotFoundException.class);
        ResponseEntity<Notification> response = (ResponseEntity<Notification>) notificationController.updateNotification(notification);
 
        verify(notificationService).updateNotification(notification);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
 
    @Test
    public void testDeleteNotification_NotFound() throws NotificationNotFoundException {
        Long id = 2L;
        doThrow(NotificationNotFoundException.class).when(notificationService).deleteNotification(id);
        ResponseEntity<Void> response = (ResponseEntity<Void>) notificationController.deleteNotification(id);
 
        verify(notificationService).deleteNotification(id);
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}
