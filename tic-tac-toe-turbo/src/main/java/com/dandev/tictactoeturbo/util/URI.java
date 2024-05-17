package com.dandev.tictactoeturbo.util;

import com.dandev.tictactoeturbo.infra.exceptions.UriParamsError;
import lombok.*;
import org.apache.http.util.Asserts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ToString
@EqualsAndHashCode
public class URI implements Serializable {

    @Getter
    private String uri;
    private final Map<String, String> params = new HashMap<>();

    public URI(@NonNull String uri) {
        init(uri);
    }


    private void init(String uri) {
        String[] uriAndParams = uri.split("\\?");
        this.uri = uriAndParams[0];
        if (uriAndParams.length > 1) {
            String[] paramsAndValues = uriAndParams[1].split("&");
            for (String paramAndValue : paramsAndValues) {
                if (paramAndValue.split("=").length < 2) throw new UriParamsError("Erro ao ler parametros da uri "+ List.of(paramsAndValues));

                String param = paramAndValue.split("=")[0];
                String value = paramAndValue.split("=")[1];

                if (params.containsKey(param)) throw new RuntimeException("Param key duplicate");

                params.put(paramAndValue.split("=")[0], paramAndValue.split("=")[1]);
            }
        }
    }
    public static URI uri(String uri){
        return new URI(uri);
    }

    public Optional<String> getParam(String param) {
        if (params.containsKey(param)) return Optional.of(params.get(param));
        return Optional.empty();
    }

    public void addParam(String key, String value) {

        this.params.put(key, value);

    }


}