package au.com.mason.expensemanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.mason.expensemanager.dao.NotificationDao;
import au.com.mason.expensemanager.domain.Notification;
import au.com.mason.expensemanager.dto.NotificationDto;
import au.com.mason.expensemanager.mapper.NotificationMapperWrapper;

@Component
public class NotificationService {
	
	@Autowired
	private NotificationMapperWrapper notificationMapperWrapper;
	
	@Autowired
	private NotificationDao notificationDao;
	
	public Notification create(Notification notification) {
		return notificationDao.create(notification);
	}
	
	public List<NotificationDto> getAll() throws Exception {
		List<NotificationDto> notificationDtos = new ArrayList<>();
		for(Notification notification : notificationDao.getUnread()) {
			notificationDtos.add(notificationMapperWrapper.notificationToNotificationDto(notification));
		};
		
		return notificationDtos;
	}
	
	public NotificationDto markRead(Long id) throws Exception {
		Notification notification = notificationDao.getById(id);
		notification.setRead(true);
		
		notificationDao.update(notification);
		
		return notificationMapperWrapper.notificationToNotificationDto(notification);
	}
	
	public NotificationDto markUnRead(Long id) throws Exception {
		Notification notification = notificationDao.getById(id);
		notification.setRead(false);
		
		notificationDao.update(notification);
		
		return notificationMapperWrapper.notificationToNotificationDto(notification);
	}

}
