package com.lanchonete.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lanchonete.order.model.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

//anotação entity define a classe como sendo parte da modelagem que refrete no banco de dados
@Entity
@Table(name="tb_clients")//estamos apenas definindo nome da tabela como sendo tb_clients
public class Client {
	
	
	@Id //anotação que diz que este atributo vai assumir papel de chave primária
	@GeneratedValue(strategy=GenerationType.IDENTITY) //forma como a chave primária vai ser gerada, identidade da linha sequencia numérica administrada pelo banco
	private Long id;
	
	@NotEmpty(message="The atribute Name can't to be empty")//não deve aceitar o atributo name em branco, sem um dado
	private String name;
	
	private Long document;//para receber o CPF
	
	//tipo de relacionamento definido como um cliente pode ter vários pedidos
	//a forma como as informações vão ser acionadas no banco será uma busca Lazy, lenta
	//mapeamento do relacionamento sera o client  então la no json vamos ter que criar esse objeto ao criar um novo pedido
	//Efeito ao deletar um cliente apaga tbm os pedidos relacionados a este cliente
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("client") // evitar recursividade ao trazer dados relacionados
	private List<Order> order; //

	
	//getteres and setteres para permitir manipular os dados (get - recuperar/ set - imputar)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
	
}
