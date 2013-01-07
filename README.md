# Simple OAuth Server

## Installation
```shell
sudo dpkg -i oauthd_1.0.0.deb
```

## OAuth Services
### Obtain OAuth access token
```shell
curl -X POST \
    --user <client_id>:<client_secret> \
    --data "grant_type=password" \
    --data "username=<login>" \
    --data "password=<password>" \
    http://localhost:8081/oauth/token
```

## User Management Services
### Check if login is free
```shell
curl "http://localhost:8081/user/check-login-free?login=<login>"
```

### Register new user
```shell
curl -X POST \
    --header "Content-Type: application/json;charset=UTF-8" \
    --data '{ "login" : "<login>", "password" : "<password>" }' \
    http://localhost:8081/user
```

### Get user info
```shell
curl --header "Authorization: Bearer <your token here>" http://localhost:8081/user
```

### Change password
```shell
curl -X PATCH \
    --header "Authorization: Bearer <your token here>" \
    --header "Content-Type: application/json;charset=UTF-8" \
    --data '{ "password" : "<new_password>" }' \
    http://localhost:8081/user
```

### Delete user
```shell
curl -X DELETE \
    --header "Authorization: Bearer <your token here>" \
    http://localhost:8081/user
```

## Client Management Services
Available for `root` user only

### List all clients
```shell
curl http://localhost:8081/client
```

### Register new client
```shell
curl -X POST \
    --header "Content-Type: application/json;charset=UTF-8" \
    --data '{ "client_id" : "<client_id>", "client_secret" : "<client_secret>" }' \
    http://localhost:8081/client
```

### Update client
```shell
curl -X PATCH \
    --header "Content-Type: application/json;charset=UTF-8" \
    --data '{ "client_secret" : "<client_secret>" }' \
    http://localhost:8081/client/<client_id>
```

### Delete client
```shell
curl -X DELETE \
    --user <client_id>:<client_secret> \
    http://localhost:8081/client
```
