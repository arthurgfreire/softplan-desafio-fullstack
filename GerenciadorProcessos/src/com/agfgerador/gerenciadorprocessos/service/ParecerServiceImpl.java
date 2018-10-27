package com.agfgerador.gerenciadorprocessos.service;

/**ParecerServiceImpl - Classe de comnica��o com os DAO e Service.
 * 
 * @author Arthur Freire
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agfgerador.autenticacao.domain.Metodo;
import com.agfgerador.autenticacao.domain.Modulo;
import com.agfgerador.autenticacao.service.LogService;
import com.agfgerador.gerenciadorprocessos.dao.ParecerDAO;


import com.agfgerador.compartilhado.domain.ObjetoPadraoSemId;
@Service("parecerService")
public class ParecerServiceImpl implements ParecerService {

	private ParecerDAO parecerDAO;

	@Autowired
	private LogService logService;

	@Autowired
	public ParecerServiceImpl(ParecerDAO parecerDAO) {
		this.parecerDAO = parecerDAO;
	}
	/**M�todo de inserir e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void createNew(ObjetoPadraoSemId obj) {
		parecerDAO.persist(obj);
		logService.createNewSemId(obj, Modulo.PROJETOBASE, Metodo.ADICIONAR);
	}
	/**M�todo de atualizar e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void update(ObjetoPadraoSemId obj) {
		parecerDAO.update(obj);
		logService.createNewSemId(obj, Modulo.PROJETOBASE, Metodo.ATUALIZAR);
	}
	/**M�todo de deletar e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void remove(ObjetoPadraoSemId obj) {
		parecerDAO.delete(obj);
		logService.createNewSemId(obj, Modulo.PROJETOBASE, Metodo.REMOVER);
	}
	/**M�todo de busca pelo Id.
	 * 
	 * @author Arthur Freire
	 * @param id Long - Objeto da classe.
	 */
	@Transactional(readOnly=true)
	public ObjetoPadraoSemId findById(Long id) {
		return parecerDAO.loadById(id);
	}
	/**M�todo listar todos os objetos.
	 * 
	 * @author Arthur Freire
	 */
	@Override
	public List<ObjetoPadraoSemId> findAll() {
		return parecerDAO.findAll();
	}
	/**M�todo listar todos os objetos da pagina��o.
	 * 
	 * @author Arthur Freire
	 * @param pagesize int - quantidade que ser� listado.
	 * @param page int - De qual posi��o ir� come�ar a busca.
	 * 
	 * @return List<ObjetoPadrao> - lista de Objetos.
	 */
	@Override
	public List<ObjetoPadraoSemId> findAll(int pagesize, int page) {
		return parecerDAO.findAll( pagesize,  page);
	}
	/**M�todo conta todos os objetos da classe no banco de dados.
	 * 
	 * @author Arthur Freire
	 * @return long - total dos objetos.
	 */
	@Override
	public Long getNumberRecords() {
		return parecerDAO.getNumberRecords();     
	}
	/**M�todo listar com filtros.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 * 
	 * @return List<ObjetoPadrao> - lista de Objetos.
	 */
	@Transactional(readOnly=true)
	public List<ObjetoPadraoSemId> filter(ObjetoPadraoSemId obj) {
		return parecerDAO.filter(obj);
	}

	@Override
	public List<ObjetoPadraoSemId> findAll(String value, int pagesize, int page) {
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
	@Override
	public List<ObjetoPadraoSemId> filter(ObjetoPadraoSemId obj, int pagesize, int page){
		return parecerDAO.filter(obj, pagesize, page);
	}
	/**M�todo listar com filtros todos os objetos.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 * 
	 * @return List<ObjetoPadrao> - lista de Objetos.
	 */
	@Override
	public Long getNumberRecordsFilter(ObjetoPadraoSemId obj){
		return parecerDAO.getNumberRecordsFilter(obj);
	}
	/**M�todo de inserir e tamb�m registra o que foi feito no log - Exclusivo para objetos que n�o tem o id padr�o.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Override
	public void createNewSemId(ObjetoPadraoSemId obj) {
		parecerDAO.persist(obj);
		logService.createNewSemId(obj, Modulo.PROJETOBASE, Metodo.ADICIONAR);
	}

}
