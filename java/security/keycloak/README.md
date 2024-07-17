### 筆記:
```
# 起docker
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.1 start-dev
http://localhost:8080/realms/Boyen/.well-known/openid-configuration
https://jwt.io/

重點 : 要把Verify Profile關掉，不然General會變必填
```

### ref:
```
https://www.youtube.com/watch?v=vmEWywGzWbA
```

