package com.abcall.clientes.persistence.entity.compositekey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserClientPKTest {

    @Test
    void equalsAndHashCode_ReturnsTrue_ForSameValues() {
        UserClientPK pk1 = new UserClientPK();
        pk1.setTipoDocUsuario("CC");
        pk1.setNumeroDocUsuario(123456L);
        pk1.setIdCliente(789012);

        UserClientPK pk2 = new UserClientPK();
        pk2.setTipoDocUsuario("CC");
        pk2.setNumeroDocUsuario(123456L);
        pk2.setIdCliente(789012);

        assertEquals(pk1, pk2);
        assertEquals(pk1.hashCode(), pk2.hashCode());
    }

    @Test
    void equalsAndHashCode_ReturnsFalse_ForDifferentValues() {
        UserClientPK pk1 = new UserClientPK();
        pk1.setTipoDocUsuario("CC");
        pk1.setNumeroDocUsuario(123456L);
        pk1.setIdCliente(789012);

        UserClientPK pk2 = new UserClientPK();
        pk2.setTipoDocUsuario("TI");
        pk2.setNumeroDocUsuario(654321L);
        pk2.setIdCliente(210987);

        assertNotEquals(pk1, pk2);
        assertNotEquals(pk1.hashCode(), pk2.hashCode());
    }

    @Test
    void equals_ReturnsFalse_WhenComparedWithNull() {
        UserClientPK pk = new UserClientPK();
        pk.setTipoDocUsuario("CC");
        pk.setNumeroDocUsuario(123456L);
        pk.setIdCliente(789012);

        assertNotEquals(null, pk);
    }

    @Test
    void equals_ReturnsFalse_WhenComparedWithDifferentClass() {
        UserClientPK pk = new UserClientPK();
        pk.setTipoDocUsuario("CC");
        pk.setNumeroDocUsuario(123456L);
        pk.setIdCliente(789012);

        assertNotEquals("SomeString", pk);
    }
}