package com.agfgerador.gerenciadorprocessos.controller;

import java.util.List;
import java.util.ArrayList;
import org.zkoss.zul.Paging;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import com.agfgerador.compartilhado.domain.ObjetoPadraoSemId;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Image;
import com.agfgerador.compartilhado.controller.ControllerAGF;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import java.util.Map;
import org.zkoss.util.media.Media;
import org.zkforge.json.simple.JSONArray;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import java.awt.image.BufferedImage;
import com.agfgerador.compartilhado.util.AGFImagem;
import com.agfgerador.compartilhado.controller.ICam;
import com.agfgerador.compartilhado.controller.IModal;
import com.agfgerador.compartilhado.util.AGFJs;
import com.agfgerador.compartilhado.util.AGFModal;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Checkbox;
import com.agfgerador.compartilhado.util.AGFPaginacao;
import com.agfgerador.compartilhado.util.AGFUtil;
import com.agfgerador.compartilhado.controller.IPaginacao;
import com.agfgerador.compartilhado.domain.ObjetoPadrao;
import com.agfgerador.compartilhado.util.AGFComponente;
import com.agfgerador.gerenciadorprocessos.domain.Pessoa;
import com.agfgerador.gerenciadorprocessos.service.PessoaService;

/**PessoaController - Classe para controle das fun��es da View pessoamanage.zul.
 * 
 * @author Arthur Freire
 */
public class PessoaController extends ControllerAGF implements IPaginacao,IModal,ICam{
	private static final long serialVersionUID = 1L;

	public Pessoa pessoa;
	private PessoaService pessoaService;
	private int valid;
	private List<ObjetoPadrao> objs = null;
	private Pessoa compAux = new Pessoa();
	private AGFModal mbmodal;
	private Image imagem;
	private Div pics0;
	private Datebox dateboxData;

	/**Inicializa todas as fun��es da classe controller 
	 * 
	 * @author Arthur Freire
	 * @param win Componente - Dados que ser� usado na view para iniciar a classe controller. 
	 */
	public void doAfterCompose(Component win) throws Exception {
		win.setAttribute("controller",this);
		win.setAttribute("nomeTela", "Pessoa");
		mbmodal = new AGFModal();
		super.doAfterCompose(win,true,"all");
		btInformacoes.setVisible(false);
	}


	/**M�todo aonde � informado os c�digos que insere os valores na listagem da view.
	 * 
	 * @author Arthur Freire      
	 */
	public void renderizarListaPrincipal() {
		listbox.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem arg0, Object arg1) throws Exception {
				Pessoa m = (Pessoa) arg1;
				Listcell lc = new Listcell();
				lc.appendChild(new Checkbox());
				arg0.appendChild(lc);
				arg0.appendChild(new Listcell(m.getId().toString()));
				try{
					imagem = new Image();
					imagem.setContent(AGFImagem.converterByteToBufferedImage(m.getImagem()));
					Listcell cell = new Listcell();
					if(imagem.getSrc() == null) {
						cell.setImageContent(imagem.getContent());
					}else{
						cell.setImage(imagem.getSrc());
					}
					arg0.appendChild(cell);
				}catch(Exception e){
					arg0.appendChild(new Listcell("")); 
				}
				try{
					arg0.appendChild(new Listcell(m.getNome()));
				}catch(Exception e){
					arg0.appendChild(new Listcell("")); 
				}
				try{
					arg0.appendChild(new Listcell(m.getCpf()));
				}catch(Exception e){
					arg0.appendChild(new Listcell("")); 
				}
			}
		});
	}

	/**M�todo aonde � informado os c�digos que seram inicializado 
	 * as rela��es que ser� usado na forma��o da view. 
	 * 
	 * @author Arthur Freire
	 */
	public void inicializarRelacoesNpara1() {
		imagem = new Image();
		imagem.setVisible(false);
	}

	/**Inicializa � cria elementos que ser� usado para visualiza��o da imagem. 
	 * 
	 * @author Arthur Freire
	 */
	private void inicializaElementosImagem(){
		imagem.setParent(pics0);
		imagem.setHeight("100px");
		imagem.setWidth("100px");
	}

	/**Upload da foto e tratamentos para tamanho da imagem
	 * 
	 * @author Arthur Freire     
	 */
	public void onUpload$btAlterarImagem(){
		Media media = (Media) window.getAttribute("media");
		pics0.getChildren().clear();
		Map<String,Object> ret = AGFComponente.uploadImage(media, imagem,500000, pics0);
		imagem.setStyle("display: flex;");
		String erro = (String) ret.get("erro");
		imagem = (Image) ret.get("imagem");
		if(imagem==null){
			try {
				Messagebox.show(erro,"Error", Messagebox.OK, Messagebox.ERROR);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**M�todo aonde � informado os c�digos que seram introduzido nas variaveis do objeto os valores da view. 
	 * 
	 * @author Arthur Freire
	 */
	public void getRelacoesNpara1() {
		if(imagem.getContent()!=null){
			byte[] bra = AGFImagem .converterImageToByte(imagem);
			pessoa.setImagem(bra);
		}else{           pessoa.setImagem(null);
		}
		pessoa.setData(dateboxData.getValue());      
	}

	/**M�todo aonde � informado os c�digos que seram introduzido nos campos da view os valores do objeto. 
	 * 
	 * @author Arthur Freire
	 */
	public void setRelacoesNpara1() {
		pics0.getChildren().clear();
		imagem.setParent(pics0);
		imagem.setVisible(false);
		try{
			if(pessoa.getImagem()!= null){
				imagem.setContent(AGFImagem.converterByteToBufferedImage(pessoa.getImagem()));
				imagem.setVisible(true);
			}else{
				imagem.setSrc(null);
				imagem.setVisible(false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		inicializaElementosImagem();
		dateboxData.setValue(pessoa.getData());
	}

	/**M�todo aonde � informado os c�digos que ir� limpar os campos da view.  
	 * 
	 * @author Arthur Freire
	 */
	public void limparRelacoesNpara1() {
		imagem = new Image();
		imagem.setSrc(null);
		pics0.getChildren().clear();
	}

	/**M�todo aonde � informado os c�digos que ir� validar os campos da variavel do objeto.
	 * 
	 * @author Arthur Freire
	 */
	public boolean isValidForm() {
		boolean ret = true;
		valid = 0;
		if((pessoa.getNome()==null)||(pessoa.getNome().equals(""))){
			valid = 3;
			ret = false;
		}else if ((pessoa.getCpf()!=null)&&(!AGFUtil.isValidCPF(pessoa.getCpf()))){
			valid = 5;
			ret = false;
		}
		return ret;
	}

	/**M�todo do bot�o salvar, salva ou alterar aonde � informado os c�digos que ir� imprimir a mensagem de erro na tela para os usu�rios.
	 * 
	 * @author Arthur Freire
	 */
	public void onClickbtSalvar() throws InterruptedException {
		Pessoa comp = (Pessoa) btSalvar(pessoa, pessoaService);
		if(comp!=null){
			pessoa = comp;
		}
		else {
			switch (valid) {
			case 3:
				AGFComponente.showMessage("info","Informe o campo: NOME.");
				break;  
			case 5:
				AGFComponente.showMessage("alerta","Informe o CPF v�lido para o campo: CPF.");
				break;
			}
		}
	}

	/**M�todo para fazer Busca com filtros quando apertado enter nos campos de busca.
	 * 
	 * @author Arthur Freire
	 * @param event - campo para informar eventos.
	 */
	public void onOK$auxhead(Event event) {
		compAux = new Pessoa();
		String idAux = 	((Textbox) auxhead.getFellow("filtroCodigo")).getValue();
		if(idAux.equals("")){
			compAux.setId(0L);
		}else {
			compAux.setId(Long.valueOf(idAux));
		}
		compAux.setNome(((Textbox) auxhead.getFellow("filtroNome")).getValue());
		compAux.setData(null);
		compAux.setCpf(((Textbox) auxhead.getFellow("filtroCpf")).getValue());
		int totalSize = 0;
		objs = new ArrayList<ObjetoPadrao>();
		objs = pessoaService.filter(compAux, pageSize, AGFPaginacao.getPagePaginacao(new Paging(),pageSize,0));
		totalSize = pessoaService.getNumberRecordsFilter(compAux).intValue();
		paginacao.setActivePage(0);
		AGFPaginacao.btListaPaginacao(listbox, paginacao, pageSize, pessoaService,totalSize,objs);
		AGFPaginacao.paginacao(listbox, paginacao, pageSize, 0, pessoaService, objs,null);
	}

	/**M�todo do bot�o novo, abre o formul�rio para cadastro de um novo objeto.
	 * 
	 * @author Arthur Freire
	 */
	public void onClickbtNovo() {
		pessoa = new Pessoa();
		btNovo();
	}

	/**M�todo do bot�o cancelar, cancela alguma altera��o nova.
	 * 
	 * @author Arthur Freire
	 * @deprecated
	 */
	public void onClickbtCancelar() {
		pessoa = (Pessoa) btCancelar(pessoa, pessoaService);
	}     
	/**M�todo do bot�o remover, remove um oou varios objetos.
	 * 
	 * @author Arthur Freire
	 */
	public void onClickbtRemover() throws InterruptedException {
		btRemover(pessoa, pessoaService);
	}
	/**M�todo do bot�o listar, lista os objetos existentes.
	 * 
	 * @author Arthur Freire
	 */
	public void onClickbtLista() {
		btLista();
		AGFPaginacao.btListaPaginacao(listbox, paginacao, pageSize, pessoaService, null, null);
		objs = null;
	}
	/**M�todo dos bot�es de pagina��o, jagina os objetos da classe.
	 * 
	 * @author Arthur Freire
	 */
	public void onPaging$paginacao() {
		if(objs!=null){
			objs = pessoaService.filter(compAux, pageSize, AGFPaginacao.getPagePaginacao(paginacao,pageSize,paginaAnterior));
		}
		AGFPaginacao.paginacao(listbox, paginacao, pageSize, paginaAnterior, pessoaService, objs,null);
	}
	/**M�todo usado quando for clicado no objeto da lista ele carregar� o objeto na tela.
	 * 
	 * @author Arthur Freire
	 * @param obj Object - Objeto da classe
	 */
	public void carregarObjeto(Object obj) {
		pessoa = (Pessoa) obj;
		carregarObj(pessoa);
	}

	/**M�todo usado para remover dependencias da classe pai.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Classe pai.
	 * 
	 * @return Boolean - True / False
	 * @deprecated
	 */
	@Override
	public Boolean removeDependencias(ObjetoPadrao obj) {
		return null;
	}

	/**M�todo usado para remover dependencias da classe pai de um objeto sem id padrao.
	 * 
	 * @author Arthur Freire
	 * @param obj ObjetoPadrao - Classe pai.
	 * 
	 * @return Boolean - True / False
	 * @deprecated
	 */
	@Override
	public Boolean removeDependencias(ObjetoPadraoSemId obj) {
		return null;
	}

	/**M�todo para carregar a imagem no campo imagem da view.
	 * 
	 * @author Arthur Freire
	 * @param img Image - Imagem que ser� carregada
	 * @param imgb BufferedImagem 
	 */
	@Override
	public void setImageWebCam(Image img, BufferedImage imgb) {
		Clients.evalJavaScript("setImagem('"+img.getUuid()+"', '"+imagem.getUuid()+"')");
		pics0.getChildren().clear();
		imagem.setParent(pics0);
		imagem.setContent(imgb);
		imagem.setVisible(true);
	}
	/**M�todo do bot�o salvar da tela de imagem da webcam.
	 * 
	 * @author Arthur Freire
	 */
	@Override
	public void onModal(){
		barraFerramentasButtons = new JSONArray();
		btSalvar = (A) barraFerramentas.getFellow("btSalvar");
		btSalvar.addForward("onClick", self, "onClickbtSalvar");
		barraFerramentasButtons = AGFJs.addItemBF(barraFerramentasButtons, btSalvar, "form");
		onClickbtNovo();
	}

	/**M�todo do bot�o webcam, abre a tela para salvar a imagem da webcam.
	 * 
	 * @author Arthur Freire
	 */
	public void onClick$btWebCam(){
		try {
			mbmodal.createModal("/view/compartilhado/view/cammanage.zul", "center", "WebCam", "400px",null,(Window)window);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
