
package br.edu.ifsul.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Telmo Junior
 */
@Entity
@Table(name = "foto")
public class Foto implements Serializable{
    
    @Id
    @SequenceGenerator(name = "seq_foto", sequenceName = "seq_foto_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_foto", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @NotNull(message = "O nome da foto deve ser informada")
    @NotBlank(message = "O nome da foto não pode ser em branco")
    @Length(max = 100, message = "O nome do fota não pode ter mais que {max} caracteres")
    @Column(name = "nome_foto", nullable = false, length = 100)
    private String nomeFoto;
            
    
    @NotNull(message = "A descrição deve ser informada")
    @NotBlank(message = "A descrição não pode ser em branco")
    @Length(max = 50, message = "A descrição do foto não pode ter mais que {max} caracteres")
    @Column(name = "descricao", nullable = false, length = 50)  
    private String descricao;
    
    @NotNull(message = "A foto deve ser informada")
    @Column(name = "foto", nullable = false)
    @Lob
    private byte[] arquivo;
    
    @NotNull(message = "O ordem de serviço deve ser informada")
    @ManyToOne
    @JoinColumn(name = "ordemServico", referencedColumnName = "id", nullable = false, 
            foreignKey = @ForeignKey(name = "fk_foto_ordem_servico"))
    private OrdemServico ordemServico;//bidirecional
    
    public Foto(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getNomeFoto() {
        return nomeFoto;
    }

    public void setNomeFoto(String nomeFoto) {
        this.nomeFoto = nomeFoto;
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
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Foto other = (Foto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
