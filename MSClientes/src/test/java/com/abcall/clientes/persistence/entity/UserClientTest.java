package com.abcall.clientes.persistence.entity;

import com.abcall.clientes.persistence.entity.compositekey.ClienteUsuarioPK;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserClientTest {

    @Test
    void userClient_HasCorrectEmbeddedId() {
        ClienteUsuarioPK pk = new ClienteUsuarioPK();
        pk.setTipoDocUsuario("CC");
        pk.setNumeroDocUsuario(123456L);
        pk.setNumeroDocCliente(789012L);

        UserClient userClient = new UserClient();
        userClient.setClienteUsuarioPK(pk);

        assertNotNull(userClient.getClienteUsuarioPK());
        assertEquals("CC", userClient.getClienteUsuarioPK().getTipoDocUsuario());
        assertEquals(123456L, userClient.getClienteUsuarioPK().getNumeroDocUsuario());
        assertEquals(789012L, userClient.getClienteUsuarioPK().getNumeroDocCliente());
    }

    @Test
    void userClient_AllowsNullEmbeddedId() {
        UserClient userClient = new UserClient();
        userClient.setClienteUsuarioPK(null);

        assertNull(userClient.getClienteUsuarioPK());
    }
}