package ru.atconsulting.bigdata.domain.answer;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by NSkovpin on 22.06.2016.
 */
public class StepAnswerType implements UserType {

    public static final String NAME = "stepAnswerType";

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.BINARY };
    }

    @Override
    public Class returnedClass() {
        return Answer.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == null ? o1 == null : o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        return SerializationUtils.deserialize(resultSet.getBytes(strings[0]));
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.BINARY);
            return;
        }
        preparedStatement.setBytes(i, SerializationUtils.serialize(o));
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
