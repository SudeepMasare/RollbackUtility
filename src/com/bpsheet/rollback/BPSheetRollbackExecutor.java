package com.bpsheet.rollback;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bpsheet.rollback.bo.AuditBO;
import com.bpsheet.rollback.bo.ComponentBO;
import com.bpsheet.rollback.bo.DisplayObjectsBO;
import com.bpsheet.rollback.bo.FunctionsBO;
import com.bpsheet.rollback.bo.GroupsBO;
import com.bpsheet.rollback.bo.MenusBO;
import com.bpsheet.rollback.bo.PageBO;
import com.bpsheet.rollback.bo.ProcessBO;
import com.bpsheet.rollback.bo.ServicesBO;
import com.bpsheet.rollback.bo.ViewsBO;
import com.bpsheet.rollback.configuration.HibernateConfiguration;
import com.bpsheet.rollback.so.BPSheetIdsSO;

/**
 * @author SudeepMasare
 * 
 *         Main Method
 *
 */
public class BPSheetRollbackExecutor {

	static String executorOfBPSheet = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BPSheetRollbackExecutor bpSheetRollbacker = new BPSheetRollbackExecutor();
		Session session = HibernateConfiguration.getSessionFactory().openSession();
		executorOfBPSheet = args[0];
		AuditBO auditBO = isExecutorAvailable(session, executorOfBPSheet);

		if (args[1].equalsIgnoreCase("PRE")) {
			preExecution(bpSheetRollbacker, auditBO, session);
		}

		if (args[1].equalsIgnoreCase("POST")) {
			postExecution(bpSheetRollbacker, auditBO, session);
		}

		if (args[1].equalsIgnoreCase("ROLLBACK")) {
			rollbackBPSheet(executorOfBPSheet, auditBO, session);
		}

		System.exit(0);

	}

	private static AuditBO isExecutorAvailable(Session session, String executoeName) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(AuditBO.class);
		criteria.add(Restrictions.eq("executorName", executoeName));
		List<AuditBO> results = criteria.list();
		if (!results.isEmpty()) {
			for (AuditBO auditBO : results) {
				return auditBO;
			}
		}
		return null;
	}

	private static void preExecution(BPSheetRollbackExecutor studentAddressStorage, AuditBO auditBO, Session session) {

		BPSheetIdsSO bpSheetIdsSO = new BPSheetIdsSO();

		/**
		 * 
		 * Following method call will give us Pre-BPSheet Execution Max Ids of all the
		 * tables.
		 * 
		 */
		bpSheetIdsSO = PreBPSheetAllTablesMaxIds(session, bpSheetIdsSO);

		/**
		 * 
		 * In the following method we will check if All the Max columns in Audit table
		 * are null, If all the Max columns are null then that means After the
		 * successful execution of BPSheet, we will be having new Max Ids for all the
		 * BPSheet corresponding table which we have to store in Max column of Audit
		 * table.
		 * 
		 * Remember : Now we have both Min Id = Max Id of tables before BPSheet
		 * execution Remember : & Max Id = Max Id of tables after BPSheet execution
		 * 
		 * This difference is nothing but all those records which belongs to you in case
		 * if you want to run roll-back.
		 * 
		 */

		bpSheetIdsSO = PostBPSheetAuditTableMinIds(session, bpSheetIdsSO);

		if (auditBO == null) {

			auditBO = new AuditBO();
			auditBO.setExecutorName(executorOfBPSheet);

			auditBO.setCompMinId(bpSheetIdsSO.getComponentPreId());
			auditBO.setFunsMinId(bpSheetIdsSO.getFunctionsPreId());
			auditBO.setGroupMinId(bpSheetIdsSO.getGroupPreId());
			auditBO.setMenusMinId(bpSheetIdsSO.getMenusPreId());
			auditBO.setPageMinId(bpSheetIdsSO.getPagePreId());
			auditBO.setProcessMinId(bpSheetIdsSO.getProcessPreId());
			auditBO.setDisplayObjectMinId(bpSheetIdsSO.getDisplayObjectPreId());
			auditBO.setServiceMinId(bpSheetIdsSO.getServicePreId());
			auditBO.setViewMinId(bpSheetIdsSO.getViewPreId());

		}

		studentAddressStorage.dataStorageCall(session, auditBO);

		studentAddressStorage.getFilteredRecords(auditBO.getId());

		session.close();
	}

	private static void postExecution(BPSheetRollbackExecutor studentAddressStorage, AuditBO auditBO, Session session) {

		BPSheetIdsSO bpSheetIdsSO = new BPSheetIdsSO();

		/**
		 * 
		 * Following method call will give us Pre-BPSheet Execution Max Ids of all the
		 * tables.
		 * 
		 */
		bpSheetIdsSO = PreBPSheetAllTablesMaxIds(session, bpSheetIdsSO);

		/**
		 * 
		 * In the following method we will check if All the Max columns in Audit table
		 * are null, If all the Max columns are null then that means After the
		 * successful execution of BPSheet, we will be having new Max Ids for all the
		 * BPSheet corresponding table which we have to store in Max column of Audit
		 * table.
		 * 
		 * Remember : Now we have both Min Id = Max Id of tables before BPSheet
		 * execution Remember : & Max Id = Max Id of tables after BPSheet execution
		 * 
		 * This difference is nothing but all those records which belongs to you in case
		 * if you want to run roll-back.
		 * 
		 */

		bpSheetIdsSO = PostBPSheetAuditTableMinIds(session, bpSheetIdsSO);

		if ((auditBO.getId()) != null) {
			auditBO.setCompMaxId(bpSheetIdsSO.getComponentPreId());
			auditBO.setFunsMaxId(bpSheetIdsSO.getFunctionsPreId());
			auditBO.setGroupMaxId(bpSheetIdsSO.getGroupPreId());
			auditBO.setMenusMaxId(bpSheetIdsSO.getMenusPreId());
			auditBO.setPageMaxId(bpSheetIdsSO.getPagePreId());
			auditBO.setProcessMaxId(bpSheetIdsSO.getProcessPreId());
			auditBO.setDisplayObjectMaxId(bpSheetIdsSO.getDisplayObjectPreId());
			auditBO.setServiceMaxId(bpSheetIdsSO.getServicePreId());
			auditBO.setViewMaxId(bpSheetIdsSO.getViewPreId());
		}

		studentAddressStorage.dataStorageCall(session, auditBO);

		studentAddressStorage.getFilteredRecords(auditBO.getId());

		session.close();
	}

	private static void rollbackBPSheet(String executorName, AuditBO auditBO, Session session) {
		// TODO Auto-generated method stub

		rollbackFromComponent(executorName, session);
		rollbackFromFunctions(executorName, session);
		rollbackFromGroups(executorName, session);
		rollbackFromMenus(executorName, session);
		rollbackFromPage(executorName, session);
		rollbackFromProcess(executorName, session);
		rollbackFromDisplayObject(executorName, session);
		rollbackFromService(executorName, session);
		rollbackFromViews(executorName, session);

		rollingBackExecutor(executorName, session);

	}

	private static void rollbackFromComponent(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from ComponentBO where componentId > :min and componentId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getCompMinId())
						.setInteger("max", auditBo.getCompMaxId()).executeUpdate();
				System.out.println("Rollback From Component Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromFunctions(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from FunctionsBO where functionsId > :min and functionsId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getFunsMinId())
						.setInteger("max", auditBo.getFunsMaxId()).executeUpdate();
				System.out.println("Rollback From Functions Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromGroups(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from GroupsBO where groupId > :min and groupId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getGroupMinId())
						.setInteger("max", auditBo.getGroupMaxId()).executeUpdate();
				System.out.println("Rollback From Groups Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromMenus(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from MenusBO where menusId > :min and menusId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getMenusMinId())
						.setInteger("max", auditBo.getMenusMaxId()).executeUpdate();
				System.out.println("Rollback From Menus Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromPage(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from PageBO where pageId > :min and pageId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getPageMinId())
						.setInteger("max", auditBo.getPageMaxId()).executeUpdate();
				System.out.println("Rollback From Page Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromProcess(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from ProcessBO where processId > :min and processId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getProcessMinId())
						.setInteger("max", auditBo.getProcessMaxId()).executeUpdate();
				System.out.println("Rollback From Process Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromDisplayObject(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from DisplayObjectsBO where displayObjectsId > :min and displayObjectsId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getDisplayObjectMinId())
						.setInteger("max", auditBo.getDisplayObjectMaxId()).executeUpdate();
				System.out.println("Rollback From DisplayObjects Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromService(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from ServicesBO where serviceId > :min and serviceId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getServiceMinId())
						.setInteger("max", auditBo.getServiceMaxId()).executeUpdate();
				System.out.println("Rollback From Service Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollbackFromViews(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				String hql = "delete from ViewsBO where viewId > :min and viewId <= :max";
				session.createQuery(hql).setInteger("min", auditBo.getViewMinId())
						.setInteger("max", auditBo.getViewMaxId()).executeUpdate();
				System.out.println("Rollback From Views Table Completed");
				transaction.commit();
			}
		}
	}

	private static void rollingBackExecutor(String executorName, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("executorName"), executorName));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {
				Transaction transaction = session.beginTransaction();
				session.delete(auditBo);
				System.out.println("BPSheet Executor Rolled Back From AUDIT Table");
				transaction.commit();
			}
		}
	}

	private static BPSheetIdsSO PreBPSheetAllTablesMaxIds(Session session, BPSheetIdsSO bpSheetMinMaxIdsSO) {

		DetachedCriteria componentCriteria = DetachedCriteria.forClass(ComponentBO.class)
				.setProjection(Projections.max("componentId"));
		bpSheetMinMaxIdsSO.setComponentPreId((Integer) componentCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria functionsCriteria = DetachedCriteria.forClass(FunctionsBO.class)
				.setProjection(Projections.max("functionsId"));
		bpSheetMinMaxIdsSO.setFunctionsPreId((Integer) functionsCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria groupsCriteria = DetachedCriteria.forClass(GroupsBO.class)
				.setProjection(Projections.max("groupId"));
		bpSheetMinMaxIdsSO.setGroupPreId((Integer) groupsCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria menusCriteria = DetachedCriteria.forClass(MenusBO.class)
				.setProjection(Projections.max("menusId"));
		bpSheetMinMaxIdsSO.setMenusPreId((Integer) menusCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria pageCriteria = DetachedCriteria.forClass(PageBO.class)
				.setProjection(Projections.max("pageId"));
		bpSheetMinMaxIdsSO.setPagePreId((Integer) pageCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria processCriteria = DetachedCriteria.forClass(ProcessBO.class)
				.setProjection(Projections.max("processId"));
		bpSheetMinMaxIdsSO.setProcessPreId((Integer) processCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria displayObjectCriteria = DetachedCriteria.forClass(DisplayObjectsBO.class)
				.setProjection(Projections.max("displayObjectsId"));
		bpSheetMinMaxIdsSO
				.setDisplayObjectPreId((Integer) displayObjectCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria serviceCriteria = DetachedCriteria.forClass(ServicesBO.class)
				.setProjection(Projections.max("serviceId"));
		bpSheetMinMaxIdsSO.setServicePreId((Integer) serviceCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria viewCriteria = DetachedCriteria.forClass(ViewsBO.class)
				.setProjection(Projections.max("viewId"));
		bpSheetMinMaxIdsSO.setViewPreId((Integer) viewCriteria.getExecutableCriteria(session).list().get(0));

		return bpSheetMinMaxIdsSO;
	}

	private static BPSheetIdsSO PostBPSheetAuditTableMinIds(Session session, BPSheetIdsSO bpSheetMinMaxIdsSO) {

		DetachedCriteria componentCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("compMinId"));
		bpSheetMinMaxIdsSO.setComponentPostId((Integer) componentCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria functionsCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("funsMinId"));
		bpSheetMinMaxIdsSO.setFunctionsPostId((Integer) functionsCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria groupsCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("groupMinId"));
		bpSheetMinMaxIdsSO.setFunctionsPostId((Integer) groupsCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria menusCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("menusMinId"));
		bpSheetMinMaxIdsSO.setMenusPostId((Integer) menusCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria pageCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("pageMinId"));
		bpSheetMinMaxIdsSO.setPagePostId((Integer) pageCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria processCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("processMinId"));
		bpSheetMinMaxIdsSO.setProcessPostId((Integer) processCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria displayObjectCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("displayObjectMinId"));
		bpSheetMinMaxIdsSO
				.setDisplayObjectPostId((Integer) displayObjectCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria serviceCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("serviceMinId"));
		bpSheetMinMaxIdsSO.setServicePostId((Integer) serviceCriteria.getExecutableCriteria(session).list().get(0));

		DetachedCriteria viewCriteria = DetachedCriteria.forClass(AuditBO.class)
				.setProjection(Projections.max("viewMinId"));
		bpSheetMinMaxIdsSO.setViewPostId((Integer) viewCriteria.getExecutableCriteria(session).list().get(0));

		return bpSheetMinMaxIdsSO;
	}

	public void dataStorageCall(Session session, AuditBO auditBO) {
		// TODO Auto-generated method stub
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(auditBO);
		transaction.commit();
	}

	/**
	 * The following method is only for Printing Purpose
	 */
	public void getFilteredRecords(Integer id) {

		Session session = HibernateConfiguration.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<AuditBO> criteriaQuery = builder.createQuery(AuditBO.class);
		Root<AuditBO> sRoot = criteriaQuery.from(AuditBO.class);
		criteriaQuery.select(sRoot).where(builder.equal(sRoot.get("id"), id));
		List<AuditBO> auditBOs = session.createQuery(criteriaQuery).getResultList();

		if (!auditBOs.isEmpty()) {
			for (AuditBO auditBo : auditBOs) {

				System.out.println("Record ID : ");

				System.out.println(auditBo.getId());

				System.out.println("------------------------------");

				System.out.println("Pre-BPSheet Execution Rollback Min Ids");

				System.out.println(auditBo.getCompMinId());
				System.out.println(auditBo.getFunsMinId());
				System.out.println(auditBo.getGroupMinId());
				System.out.println(auditBo.getMenusMinId());
				System.out.println(auditBo.getPageMinId());
				System.out.println(auditBo.getProcessMinId());
				System.out.println(auditBo.getDisplayObjectMinId());
				System.out.println(auditBo.getServiceMinId());
				System.out.println(auditBo.getViewMinId());

				System.out.println("------------------------------");

				System.out.println("Post-BPSheet Execution Rollback Max Ids");

				System.out.println(auditBo.getCompMaxId());
				System.out.println(auditBo.getFunsMaxId());
				System.out.println(auditBo.getGroupMaxId());
				System.out.println(auditBo.getMenusMaxId());
				System.out.println(auditBo.getPageMaxId());
				System.out.println(auditBo.getProcessMaxId());
				System.out.println(auditBo.getDisplayObjectMaxId());
				System.out.println(auditBo.getServiceMaxId());
				System.out.println(auditBo.getViewMaxId());
			}
		}

	}
}
