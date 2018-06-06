package org.irodsext.template.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.irodsext.template.dao.TemplateDao;
import org.irodsext.template.entity.Template;
import org.irodsext.template.entity.TemplateElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("TemplateEntityDao")
public class TemplateDaoImpl extends GenericDaoImpl<Template> implements TemplateDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	public long getTemplateId(String templateName) {
		Template template = this.findByName(templateName);

        if (template == null) {
            return -1;
        }

        return template.getId();
	}
	
	public Template findByName(String templateName) {
		  Query<Template> q = this.sessionFactory.getCurrentSession()
	                .createQuery("from TemplateEntity where templateName = (:templateName)")
	                .setParameter("templateName", templateName);	
		  return q.uniqueResult();
	}

	public Template findById(long id) {
		 Query<Template> q = this.sessionFactory.getCurrentSession().createQuery("from TemplateEntity where id=(:id)");
	        q.setParameter("id", id);

	        return q.uniqueResult();
	}

	public boolean deleteById(long id) {
		Template template = this.findById(id);

        if (template == null) {
            return false;
        }

        this.delete(template);

        return true;
	}

	public List<TemplateElement> listTemplateElements(String template) {
		long id = this.getTemplateId(template);

        if (id > 0) {
            return this.listTemplateElements(id);
        }

        return new ArrayList<TemplateElement>();
	}

	public List<TemplateElement> listTemplateElements(Long id) {
		 Query q = this.sessionFactory.getCurrentSession()
	                .createQuery("from template_poc where template_id = :templateID");

	        q.setParameter("templateID", id);

	        return q.list();
	}
	
	
	
}
