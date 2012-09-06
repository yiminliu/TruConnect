package com.trc.dao;


import java.util.Collection;
import java.util.List;
import java.util.Date;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.domain.support.notification.NotificationMessage;
import com.trc.domain.support.notification.NotificationPage;
import com.trc.domain.support.notification.NotificationSchedule;

@Repository("hibernateNotifictionDao")
@Transactional
public class HibernateNotificationDao extends HibernateDaoSupport implements NotificationDao {

	@Autowired
	public void init(HibernateTemplate hibernateTemplate) {
	  setHibernateTemplate(hibernateTemplate);
	}
	
	@Override	
	public Integer saveNotificationSchedule(NotificationSchedule notificationSchedule) {
		notificationSchedule.setDateCreated(new Date());
		return (Integer)(getHibernateTemplate().save(notificationSchedule));
	}

	@Override
	public Integer saveNotificationMessage(NotificationMessage notificationMessage){
		return (Integer)getHibernateTemplate().save(notificationMessage);
	}		
		
	@Override
	public Integer saveNotificationPage(NotificationPage notificationPage){
		return (Integer)getHibernateTemplate().save(notificationPage);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Collection<NotificationSchedule> getAllScheduledNotifications() {
		return getHibernateTemplate().loadAll(NotificationSchedule.class);
		//String queryString = "from NotificationSchedule";
		//return getHibernateTemplate().find(queryString);
	}

	@Override
	@Transactional(readOnly=true)
	public NotificationSchedule getNotificationById(int id) { 
		//String queryString = "from NotificationSchedule n where n.id = :id";
	    NotificationSchedule n = null;
	    //List l = null;
		try{
		 //l = (List)getHibernateTemplate().find("from NotificationSchedule n where n.id = "+ id);
		 //n = (NotificationSchedule)(getHibernateTemplate().find("from NotificationSchedule n where n.id = :id", id)).get(0);
		 n = (NotificationSchedule)(getHibernateTemplate().load(NotificationSchedule.class, id));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return n;
	}
    /*
	@Override
	@Transactional(readOnly=true)
	public NotificationSchedule findByTitle(String title) {
		String queryString = "from NotificationSchedule where title = ?";
		return (NotificationSchedule)getHibernateTemplate().find(queryString, title).get(0);
	}

	
	@Override
	@Transactional(readOnly=true)
	public Collection<NotificationSchedule> findByKeyword(String keyword) {
		String queryStringTitle = "from NotificationSchedule a where a.title like ?";
		String queryStringMessage = "from NotificationSchedule a where a.message.message like ?";
		HibernateTemplate hTemplate = getHibernateTemplate();
		Collection<NotificationSchedule> collection1 = hTemplate.find(queryStringTitle, keyword);
		Collection<NotificationSchedule> collection2 = hTemplate.find(queryStringMessage, keyword);
		collection1.addAll(collection2);
		return collection1;
	}
	*/
	
	@Override
	@Transactional(readOnly=true)
	public Collection<NotificationMessage> getAllNotificationMessages() {
		//return getHibernateTemplate().loadAll(AlertSchedule.class);
		String queryString = "from NotificationMessage";
		return getHibernateTemplate().find(queryString);
	}
	
	@Override
	public Collection<NotificationPage> getAllNotificationPages(){
		String queryString = "from NotificationPage np where np.name is not null";
		return getHibernateTemplate().find(queryString);
		
	}

	@Override
	public void modify(NotificationSchedule notificationSchedule) {
		getHibernateTemplate().saveOrUpdate(notificationSchedule);
	}

	@Override
	public void deleteNotification(int id) {
		NotificationSchedule notification = getNotificationById(id);
		getHibernateTemplate().delete(notification, LockMode.PESSIMISTIC_WRITE);
	}
	
	@Override
	public NotificationMessage findNotificationMessageById(Integer id){
		return (NotificationMessage)getHibernateTemplate().load(NotificationMessage.class, id);
	}
}
