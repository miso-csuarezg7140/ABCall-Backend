package com.abcall.clientes.persistence.entity;

import com.abcall.clientes.persistence.entity.compositekey.UserClientPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario_cliente")
public class UserClient {

    @EmbeddedId
    UserClientPK userClientPK;
}
