
package br.edu.ifsul.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Telmo Junior
 */

@Embeddable
public class ContaReceberID implements Serializable{
    
    @NotNull(message = "O número da parcela deve ser informado")
    @Column(name = "numero_parcela", nullable = false)
    private Integer numeroParcela;
    
    @NotNull(message = "A Ordem de serviço deve ser informada")    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ordem_servico", referencedColumnName = "id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_contareceberid_os"))
    private OrdemServico ordemServico;
    
    public ContaReceberID(){
        
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.numeroParcela);
        hash = 53 * hash + Objects.hashCode(this.ordemServico);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContaReceberID other = (ContaReceberID) obj;
        if (!Objects.equals(this.numeroParcela, other.numeroParcela)) {
            return false;
        }
        if (!Objects.equals(this.ordemServico, other.ordemServico)) {
            return false;
        }
        return true;
    }
    
}
