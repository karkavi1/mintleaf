package org.qamatic.mintleaf.core;

import org.qamatic.mintleaf.Column;
import org.qamatic.mintleaf.MetaDataCollection;
import org.qamatic.mintleaf.MintleafException;
import org.qamatic.mintleaf.Row;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by senips on 4/16/17.
 */
public class InMemoryRow implements Row {
    private MetaDataCollection metaDataCollection;
    private List<Object> rowValues = new ArrayList<>();

    @Override
    public Object getValue(int columnIndex) throws MintleafException {
        return rowValues.get(columnIndex);
    }

    @Override
    public MetaDataCollection getMetaData() throws MintleafException {
        return this.metaDataCollection;
    }

    @Override
    public void setMetaData(MetaDataCollection metaDataCollection) {
        this.metaDataCollection = metaDataCollection;
    }


    public void setValue(int columnIndex, Object value){
        getValues().add(value);//override if you need differently
    }

    protected void setValue(int columnIndex, byte[] value, Charset charset){
        setValue(columnIndex, new String(value, charset).trim());
    }

    public void setValues(byte[] cityRecordBytes, Charset charset) {
        int bstart = 0;
        try {
            for (int i = 0; i < getMetaData().getColumnCount(); i++) {
                Column c = getMetaData().getColumn(i);
                byte[] bytes = Arrays.copyOfRange(cityRecordBytes, bstart, bstart+c.getColumnSize());
                bstart += bytes.length;
                setValue(i, bytes, charset);
            }
        } catch (SQLException e) {
            MintleafException.throwException(e);
        } catch (MintleafException e) {
            MintleafException.throwException(e);
        }
    }

    public List<Object> getValues(){
        return rowValues;
    }
}
