package fr.insalyon.creatis.vip.cli.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

public class InfoExecutionDAO {
	public void persist (InfoExecution info) {
		HibernateUtil.openSession();
		HibernateUtil.beginTransaction();
		HibernateUtil.getSession().save(info);
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
	}
	public void upadteStatusByExecutionId(String executionIdentifier,String status){
		HibernateUtil.openSession();
		HibernateUtil.beginTransaction();
		
		Query query =HibernateUtil.getSession().createQuery("Select e from InfoExecution e where ExecutionIdentifier=:id");
		query.setParameter("id", executionIdentifier);
		
		List<InfoExecution> listInfos=new ArrayList<InfoExecution>();
		for (Object l:query.list()) {
			listInfos.add((InfoExecution)l);
		}
		for (InfoExecution info:listInfos) {
			info.setStatus(status);
		}
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();
	}
}
