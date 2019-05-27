package br.edu.ifsul.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Telmo Junior
 */
@Entity
@Table(name = "ordem_servico")
public class OrdemServico implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_ordem_servico", sequenceName = "seq_ordem_servico_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_ordem_servico", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "A data de abertura não pode ser nula")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_abertura", nullable = false)
    private Calendar dataAbertura;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fechamento", nullable = true)
    private Calendar dataFechamento;

    @NotNull(message = "A descrição do problema não pode ser nula")
    @NotBlank(message = "A descrição do problema não pode ser em branco")
    @Column(name = "descricao_problema", columnDefinition = "text", nullable = false)
    private String descricaoProblema;

    @Column(name = "resolucao_problema", columnDefinition = "text")
    private String resolucaoProblema;

    @Min(message = "O valor dos produtos não pode ser negativo", value = 0)
    @NotNull(message = "O valor dos produtos deve ser informado")
    @Column(name = "valor_produtos", nullable = false, columnDefinition = "numeric(12,2)")
    private Double valorProdutos;

    @Min(message = "O valor dos serviços não pode ser negativo", value = 0)
    @NotNull(message = "O valor dos serviços deve ser informado")
    @Column(name = "valor_servicos", nullable = false, columnDefinition = "numeric(12,2)")
    private Double valorServicos;

    @Min(message = "O valor total não pode ser negativo", value = 0)
    @NotNull(message = "O valor total deve ser informado")
    @Column(name = "valor_total", nullable = false, columnDefinition = "numeric(12,2)")
    private Double valorTotal;

    @NotNull(message = "O status deve ser informado")
    @Column(name = "status", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Status Status;

    @NotNull(message = "A forma de pagamento deve ser informada")
    @Column(name = "forma_pagamento", nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @NotNull(message = "A quantidade de parcelas deve ser informada")
    @Min(message = "O valor dos produtos não pode ser negativo", value = 0)
    @Column(name = "quantidade_parcelas", nullable = false)
    private Integer quantidadeParcelas;

    @NotNull(message = "A pessoa física deve ser informada")
    @ManyToOne
    @JoinColumn(name = "pessoa_fisica", referencedColumnName = "nome_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ordem_servico_pf"))
    private PessoaFisica pessoaFisica;

    @NotNull(message = "O usuário deve ser informado")
    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "nome_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ordem_servico_usuario"))
    private Usuario usuario;

    @NotNull(message = "O equipamento deve ser informado")
    @ManyToOne
    @JoinColumn(name = "equipamento", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_ordem_servico_equipamento"))
    private Equipamento equipamento;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Foto> fotos; //agregação por composição (como é dependente, orphanRemoval = true)

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemProduto> itemProdutos; //agregação por composição  bidirecional

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL,//persistencia implicita
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemServico> itemServicos; //agregação por composição

    @OneToMany(mappedBy = "id.ordemServico", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ContaReceber> contasReceber; //agregação por composição

    public OrdemServico() {

        valorProdutos = 0.0;
        valorServicos = 0.0;
        valorTotal = 0.0;
        quantidadeParcelas = 0;
    }

    //quem sabe esses metodos podem ser apresentados a medida que forem necessários
    public void gerarContasReceber() {
        if (this.getFormaPagamento() == FormaPagamento.AVISTA) {
            ContaReceber conta = new ContaReceber();
            conta.setValor(this.valorTotal);
            conta.setValorPago(this.valorTotal);
            conta.setVencimento(this.dataFechamento);
            conta.setDataPagamento(this.dataFechamento);
            ContaReceberID id = new ContaReceberID();
            id.setNumeroParcela(1);
            id.setOrdemServico(this);
            conta.setId(id);
            conta.setOrdemServico(this);
            this.getContasReceber().add(conta);
        } else if (this.getFormaPagamento() == FormaPagamento.APRAZO) {
            Double valorParcela = this.valorTotal / this.quantidadeParcelas;
            for (int i = 1; i <= this.quantidadeParcelas; i++) {
                ContaReceber conta = new ContaReceber();
                conta.setValor(valorParcela);
                Calendar vencimento = (Calendar) this.dataFechamento.clone();
                vencimento.add(Calendar.MONTH, i);
                conta.setVencimento(vencimento);
                ContaReceberID id = new ContaReceberID();
                id.setNumeroParcela(i);
                id.setOrdemServico(this);
                conta.setId(id);
                conta.setOrdemServico(this);
                this.getContasReceber().add(conta);
            }
        }
    }

    public void atualizaValorTotal() {
        this.valorTotal = this.valorProdutos + this.valorServicos;
    }

    public void adicionarServico(ItemServico obj) {
        valorServicos += obj.getValorTotal();
        obj.setOrdemServico(this);
        this.getItemServicos().add(obj);
        atualizaValorTotal();
    }

    public void removerServico(int index) {
        ItemServico obj = this.getItemServicos().get(index);
        valorServicos -= obj.getValorTotal();
        atualizaValorTotal();
        this.getItemServicos().remove(index);
    }

    public void adicionarProduto(ItemProduto obj) {
        valorProdutos += obj.getValorTotal();
        obj.setOrdemServico(this);
        this.getItemProdutos().add(obj);
        atualizaValorTotal();
    }

    public void removerProduto(int index) {
        ItemProduto obj = this.getItemProdutos().get(index);
        valorProdutos -= obj.getValorTotal();
        atualizaValorTotal();
        this.getItemProdutos().remove(index);
    }

    public void adicionarFoto(Foto obj) {
        obj.setOrdemServico(this);
        this.getFotos().add(obj);
    }

    public void removerFoto(int index) {
        this.getFotos().remove(index);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Calendar getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Calendar dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getResolucaoProblema() {
        return resolucaoProblema;
    }

    public void setResolucaoProblema(String resolucaoProblema) {
        this.resolucaoProblema = resolucaoProblema;
    }

    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Double getValorServicos() {
        return valorServicos;
    }

    public void setValorServicos(Double valorServicos) {
        this.valorServicos = valorServicos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public List<ItemProduto> getItemProdutos() {
        return itemProdutos;
    }

    public void setItemProdutos(List<ItemProduto> itemProdutos) {
        this.itemProdutos = itemProdutos;
    }

    public List<ItemServico> getItemServicos() {
        return itemServicos;
    }

    public void setItemServicos(List<ItemServico> itemServicos) {
        this.itemServicos = itemServicos;
    }

    public List<ContaReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContaReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status Status) {
        this.Status = Status;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

}
