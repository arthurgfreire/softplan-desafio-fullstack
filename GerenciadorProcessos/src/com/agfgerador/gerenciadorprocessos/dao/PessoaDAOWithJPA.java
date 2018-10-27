 package com.agfgerador.gerenciadorprocessos.dao;

 import java.util.List;
 import javax.persistence.EntityManager;
 import javax.persistence.PersistenceContext;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.Projections;
 import org.springframework.stereotype.Repository;
 import org.springframework.transaction.annotation.Transactional;
 import com.agfgerador.compartilhado.domain.ObjetoPadrao;
 import com.agfgerador.gerenciadorprocessos.domain.Pessoa;

 	/**PessoaDAOWithJPA - Classe de manipula��o do banco de dados.
	 * 
	 * @author Arthur Freire
	 */
 
   @Repository
   @Transactional("txManagerConexao")
   public class PessoaDAOWithJPA implements PessoaDAO {

     @PersistenceContext(unitName="conexao")
     protected EntityManager entityManager;

     /**M�todo de busca pelo Id.
      * 
      * @author Arthur Freire
      * @param id Long - Objeto da classe.
      * 
      * @return ObjetoPadrao
      */
     @Override
     public ObjetoPadrao loadById(Long id) {
       return entityManager.find(Pessoa.class, id);
     }

     /**M�todo de inserir.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      */
     @Override
     public void persist(ObjetoPadrao obj) {
       entityManager.persist(obj);
     }
     
     /**M�todo de atualizar.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      */
     @Override
     public void update(ObjetoPadrao obj) {
       entityManager.merge(obj);
     }
     /**M�todo de deletar.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      */
     @Override
     public void delete(ObjetoPadrao obj) {
       entityManager.remove(entityManager.merge(obj));
     }
     /**M�todo listar todos os objetos.
      * 
      * @author Arthur Freire
      */
     @SuppressWarnings("unchecked")
     @Override
     public List<ObjetoPadrao> findAll() {
     return entityManager.createQuery("Select m from Pessoa m").getResultList();
     }

     /**M�todo listar todos os objetos da pagina��o.
      * 
      * @author Arthur Freire
      * @param pagesize int - quantidade que ser� listado.
      * @param page int - De qual posi��o ir� come�ar a busca.
      * 
      * @return List<ObjetoPadrao> - lista de Objetos.
      */
     @SuppressWarnings("unchecked")
     @Override
     public List<ObjetoPadrao> findAll(int pagesize, int page) {
       Session session  = (Session) entityManager.getDelegate();
       Criteria criteria = session.createCriteria(Pessoa.class);
       criteria.setFirstResult(page);
       criteria.setMaxResults(pagesize);
       return criteria.list();
     }

     /**M�todo conta todos os objetos da classe no banco de dados.
      * 
      * @author Arthur Freire
      * @return Long - total dos objetos.
      */
     @Override
     public Long getNumberRecords() {
     Session session = (Session) entityManager.getDelegate();
     Criteria criteria = session.createCriteria(Pessoa.class);
     criteria.setProjection(Projections.rowCount());
     return (Long) criteria.list().get(0);
     }

     /**M�todo listar com filtros.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      * 
      * @return List<ObjetoPadrao> - lista de Objetos.
      */
     @SuppressWarnings("unchecked")
     @Override
     public List<ObjetoPadrao> filter(ObjetoPadrao obj) {
       Session session = (Session) entityManager.getDelegate();
       Criteria criteria = session.createCriteria(Pessoa.class);
       if(obj.getId() != 0)
         criteria.add(Restrictions.eq("id", obj.getId()));

       if((((Pessoa)obj).getNome() != null)&&(!((Pessoa)obj).getNome().equals(""))) 
           criteria.add(Restrictions.ilike("nome",((Pessoa)obj).getNome()+"%")); 

       if((((Pessoa)obj).getData()!=null)&&(!((Pessoa)obj).getData().equals("")))
           criteria.add(Restrictions.eq("data", ((Pessoa)obj).getData()));

       if((((Pessoa)obj).getCpf() != null)&&(!((Pessoa)obj).getCpf().equals(""))) 
           criteria.add(Restrictions.ilike("cpf",((Pessoa)obj).getCpf()+"%"));

       return criteria.list();
     }

     @Override
     public List<ObjetoPadrao> findAll(String value, int pagesize, int page) {
       return null;
     }

     @Override
     public Long getNumberRecords(String value) {
       return null;
     }

     /**M�todo listar com filtros todos os objetos da pagina��o.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      * @param pagesize int - quantidade que ser� listado.
      * @param page int - De qual posi��o ir� come�ar a busca.
      * 
      * @return List<ObjetoPadrao> - lista de Objetos.
      */
     @SuppressWarnings("unchecked")
     @Override
     public List<ObjetoPadrao> filter(ObjetoPadrao obj, int pagesize, int page) {
       Session session = (Session) entityManager.getDelegate();
       Criteria criteria = session.createCriteria(Pessoa.class);
       if(obj.getId() != 0)
         criteria.add(Restrictions.eq("id", obj.getId()));

       if((((Pessoa)obj).getNome() != null)&&(!((Pessoa)obj).getNome().equals(""))) 
           criteria.add(Restrictions.ilike("nome",((Pessoa)obj).getNome()+"%")); 

         if((((Pessoa)obj).getData()!=null)&&(!((Pessoa)obj).getData().equals("")))
           criteria.add(Restrictions.eq("data", ((Pessoa)obj).getData()));

         if((((Pessoa)obj).getCpf() != null)&&(!((Pessoa)obj).getCpf().equals(""))) 
           criteria.add(Restrictions.ilike("cpf",((Pessoa)obj).getCpf()+"%"));


       criteria.setFirstResult(page);
       criteria.setMaxResults(pagesize);
       return criteria.list();
     }

     /**M�todo listar com filtros todos os objetos.
      * 
      * @author Arthur Freire
      * @param obj ObjetoPadrao - Objeto da classe.
      * 
      * @return Long - Quantidade da lista.
      */
     @Override
     public Long getNumberRecordsFilter(ObjetoPadrao obj) {
       Session session = (Session) entityManager.getDelegate();
       Criteria criteria = session.createCriteria(Pessoa.class);
       if(obj.getId() != 0)
         criteria.add(Restrictions.eq("id", obj.getId()));

       if((((Pessoa)obj).getNome() != null)&&(!((Pessoa)obj).getNome().equals(""))) 
         criteria.add(Restrictions.ilike("nome",((Pessoa)obj).getNome()+"%")); 

       if((((Pessoa)obj).getData()!=null)&&(!((Pessoa)obj).getData().equals("")))
         criteria.add(Restrictions.eq("data", ((Pessoa)obj).getData()));

       if((((Pessoa)obj).getCpf() != null)&&(!((Pessoa)obj).getCpf().equals(""))) 
         criteria.add(Restrictions.ilike("cpf",((Pessoa)obj).getCpf()+"%")); 


       criteria.setProjection(Projections.rowCount());
       return (Long) criteria.list().get(0);
     }

}