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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Telmo Junior
 */
@Entity
@Table(name = "equipamento")
public class Equipamento implements Serializable {
    
    @Id
    @SequenceGenerator(name = "seq_equipamento", sequenceName = "seq_equipamento_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_equipamento", strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @NotNull(message = "A descricao do equipamento deve ser informada")
    @NotBlank(message = "A descricao do equipamento não pode ficar em branco")
    @Length(max = 50, message = "A descricaoO do equipamento não pode ter mais que {max} caracteres")
    @Column(name = "nome_cidade", nullable = false)
    private String descricao;
    
    @NotBlank(message = "O numero de série não pode ser em branco")
    @Length(max = 30, message = "O numero de série não pode ter mais que {max} caracteres")
    @Column(name = "numero_serie", length = 30)
    private String numeroSerie;
    
    @NotNull(message = "A marca deve ser informada")
    @ManyToOne
    @JoinColumn(name = "marca", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_equipamento_marca")) 
    private Marca marca;//associação
    
    public Equipamento(){
        
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

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final Equipamento other = (Equipamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
