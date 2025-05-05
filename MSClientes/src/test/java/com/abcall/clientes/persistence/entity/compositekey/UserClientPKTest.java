package com.abcall.clientes.persistence.entity.compositekey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserClientPKTest {

    @Test
    void equalsAndHashCode_ReturnsTrue_ForSameValues() {
        UserClientPK pk1 = new UserClientPK();
        pk1.setDocumentTypeUser(1);
        pk1.setDocumentUser(123456L);
        pk1.setIdClient(789012);

        UserClientPK pk2 = new UserClientPK();
        pk2.setDocumentTypeUser(1);
        pk2.setDocumentUser(123456L);
        pk2.setIdClient(789012);

        assertEquals(pk1, pk2);
        assertEquals(pk1.hashCode(), pk2.hashCode());
    }

    @Test
    void equalsAndHashCode_ReturnsFalse_ForDifferentValues() {
        UserClientPK pk1 = new UserClientPK();
        pk1.setDocumentTypeUser(1);
        pk1.setDocumentUser(123456L);
        pk1.setIdClient(789012);

        UserClientPK pk2 = new UserClientPK();
        pk2.setDocumentTypeUser(2);
        pk2.setDocumentUser(654321L);
        pk2.setIdClient(210987);

        assertNotEquals(pk1, pk2);
        assertNotEquals(pk1.hashCode(), pk2.hashCode());
    }

    @Test
    void equals_ReturnsFalse_WhenComparedWithNull() {
        UserClientPK pk = new UserClientPK();
        pk.setDocumentTypeUser(1);
        pk.setDocumentUser(123456L);
        pk.setIdClient(789012);

        assertNotEquals(null, pk);
    }

    @Test
    void equals_ReturnsFalse_WhenComparedWithDifferentClass() {
        UserClientPK pk = new UserClientPK();
        pk.setDocumentTypeUser(1);
        pk.setDocumentUser(123456L);
        pk.setIdClient(789012);

        assertNotEquals("SomeString", pk);
    }
}