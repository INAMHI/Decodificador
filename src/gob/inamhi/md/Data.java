/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.inamhi.md;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Diego
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer dataId;
    private Integer estaId;
    private Date datafetd;
    private Double datavalo;
    private BigInteger dataince;
    private String indaId;
    private String dataobse;
    private Boolean datactrl;
    private Integer datavers;
    private String datauing;
    private Date datafing;
    private String dataumod;
    private Date datafmod;

    public Data() {
    }

    public Data(Integer dataId) {
        this.dataId = dataId;
    }

    public Data(Integer dataId, Date datafetd, String datauing, Date datafing) {
        this.dataId = dataId;
        this.datafetd = datafetd;
        this.datauing = datauing;
        this.datafing = datafing;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getEstaId() {
        return estaId;
    }

    public void setEstaId(Integer estaId) {
        this.estaId = estaId;
    }

    public Date getDatafetd() {
        return datafetd;
    }

    public void setDatafetd(Date datafetd) {
        this.datafetd = datafetd;
    }

    public Double getDatavalo() {
        return datavalo;
    }

    public void setDatavalo(Double datavalo) {
        this.datavalo = datavalo;
    }

    public BigInteger getDataince() {
        return dataince;
    }

    public void setDataince(BigInteger dataince) {
        this.dataince = dataince;
    }

    public String getIndaId() {
        return indaId;
    }

    public void setIndaId(String indaId) {
        this.indaId = indaId;
    }

    public String getDataobse() {
        return dataobse;
    }

    public void setDataobse(String dataobse) {
        this.dataobse = dataobse;
    }

    public Boolean getDatactrl() {
        return datactrl;
    }

    public void setDatactrl(Boolean datactrl) {
        this.datactrl = datactrl;
    }

    public Integer getDatavers() {
        return datavers;
    }

    public void setDatavers(Integer datavers) {
        this.datavers = datavers;
    }

    public String getDatauing() {
        return datauing;
    }

    public void setDatauing(String datauing) {
        this.datauing = datauing;
    }

    public Date getDatafing() {
        return datafing;
    }

    public void setDatafing(Date datafing) {
        this.datafing = datafing;
    }

    public String getDataumod() {
        return dataumod;
    }

    public void setDataumod(String dataumod) {
        this.dataumod = dataumod;
    }

    public Date getDatafmod() {
        return datafmod;
    }

    public void setDatafmod(Date datafmod) {
        this.datafmod = datafmod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataId != null ? dataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.dataId == null && other.dataId != null) || (this.dataId != null && !this.dataId.equals(other.dataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Data[ dataId=" + dataId + " ]";
    }
}
