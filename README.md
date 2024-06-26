# Pré-requisitos:
* Docker (ou Postgres instalado em sua máquina, entretanto este tutorial está escrito para o caso do Docker)
* Java SDK 17
* Angular 16
* Alguma IDE para desenolvimento JAVA.
* Maven (https://maven.apache.org/install.html)
* Em alguma pasta de sua preferência abra o terminal e clone o projeto:
```
git clone https://github.com/Tatu-Armadillo/rh-recruitment
```

# Como executar o Backend:
Acesse a pasta localizada no backend
```
cd .\backend\
```

Antes de executar o programa, há a necessidade de criar e executar uma imagem de um container, que será usado como banco de dados. 
```
docker-compose up -d
```

Após verificar se o container está rodando corretamente, realize o build pelo maven
```
.\mvnw clean package
```

Rodar o seguinte comando para rodar o projeto
```
.\mvnw spring-boot:run
```

Link de acesso a documentação dos endpoints 
```
http://localhost:9090/api/swagger-ui/index.html
```

# Como executar o Frontend:
Acesse a pasta localizada no backend
```
cd .\frontend\
```

Rodar o seguinte comando para rodar o projeto
```
ng serve
```

Link de acesso ao frontend
```
http://localhost:4200/
```



