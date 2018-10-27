package com.agfgerador.gerenciadorprocessos.service;

/**ProcessoServiceImpl - Classe de comnica��o com os DAO e Service.
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
import com.agfgerador.gerenciadorprocessos.dao.ProcessoDAO;


import com.agfgerador.compartilhado.domain.ObjetoPadraoSemId;
import com.agfgerador.compartilhado.domain.ObjetoPadrao;
@Service("processoService")
public class ProcessoServiceImpl implements ProcessoService {

	private ProcessoDAO processoDAO;

	@Autowired
	private LogService logService;

	@Autowired
	public ProcessoServiceImpl(ProcessoDAO processoDAO) {
		this.processoDAO = processoDAO;
	}
	/**M�todo de inserir e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void createNew(ObjetoPadrao obj) {
		processoDAO.persist(obj);
		logService.createNew(obj, Modulo.PROJETOBASE, Metodo.ADICIONAR);
	}
	/**M�todo de atualizar e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void update(ObjetoPadrao obj) {
		processoDAO.update(obj);
		logService.createNew(obj, Modulo.PROJETOBASE, Metodo.ATUALIZAR);
	}
	/**M�todo de deletar e tamb�m registra o que foi feito no log.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 */
	@Transactional
	public void remove(ObjetoPadrao obj) {
		processoDAO.delete(obj);
		logService.createNew(obj, Modulo.PROJETOBASE, Metodo.REMOVER);
	}
	/**M�todo de busca pelo Id.
	 * 
	 * @author Arthur Freire
	 * @param id Long - Objeto da classe.
	 */
	@Transactional(readOnly=true)
	public ObjetoPadrao findById(Long id) {
		return processoDAO.loadById(id);
	}
	/**M�todo listar todos os objetos.
	 * 
	 * @author Arthur Freire
	 */
	@Override
	public List<ObjetoPadrao> findAll() {
		return processoDAO.findAll();
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
	public List<ObjetoPadrao> findAll(int pagesize, int page) {
		return processoDAO.findAll( pagesize,  page);
	}
	/**M�todo conta todos os objetos da classe no banco de dados.
	 * 
	 * @author Arthur Freire
	 * @return long - total dos objetos.
	 */
	@Override
	public Long getNumberRecords() {
		return processoDAO.getNumberRecords();     }
	/**M�todo listar com filtros.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 * 
	 * @return List<ObjetoPadrao> - lista de Objetos.
	 */
	@Transactional(readOnly=true)
	public List<ObjetoPadrao> filter(ObjetoPadrao obj) {
		return processoDAO.filter(obj);
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
	@Override
	public List<ObjetoPadrao> filter(ObjetoPadrao obj, int pagesize, int page){
		return processoDAO.filter(obj, pagesize, page);
	}
	/**M�todo listar com filtros todos os objetos.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Objeto da classe.
	 * 
	 * @return List<ObjetoPadrao> - lista de Objetos.
	 */
	@Override
	public Long getNumberRecordsFilter(ObjetoPadrao obj){
		return processoDAO.getNumberRecordsFilter(obj);
	}

	@Override
	public void createNew(ObjetoPadraoSemId obj) {

	}

}
