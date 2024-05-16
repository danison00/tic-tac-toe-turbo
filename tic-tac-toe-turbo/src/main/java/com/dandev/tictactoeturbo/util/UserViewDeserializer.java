package com.dandev.tictactoeturbo.util;

import com.dandev.tictactoeturbo.socket.dtos.UserView;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.UUID;

public class UserViewDeserializer extends JsonDeserializer<UserView> {

    private TreeNode tree;

    @Override
    public UserView deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        tree = jsonParser.readValueAsTree();

        UUID id = UUID.fromString(get("id"));
        String name = get("name");

        return new UserView(id, name);

    }

    private String get(String key) {
        return tree.get(key).toString().substring(1, tree.get(key).toString().length() - 1);
    }
}
