# 🚀 InovaLab

## 💡 Sobre o Projeto

O **InovaLab** surge com o objetivo de fortalecer a conexão e engajamento entre alunos e professores na comunidade da **CESAR School**. A proposta é a criação de uma aplicação integrada à plataforma online que centralize informações e traga formas práticas de inscrição para os alunos poderem participar de iniciativas e projetos. Essa solução tem como principal finalidade, através de uma extensão colocada na comunidade, o compartilhamento de informações base das iniciativas dentro da CESAR School, mantendo os alunos e professores cientes dos projetos existentes e suas novidades. O InovaLab fomenta, assim, o interesse e o engajamento entre os membros da comunidade, deixando-a mais unida e dinâmica.

<br>

## 👀 Veja Nosso Projeto em Ação

- **Screencast**: Você pode ver um screencast do funcionamento das principais funcionalidades [aqui](https://youtu.be/nnp3lqUalrA).

- **Executar o Projeto no Seu Computador**: Se preferir, você pode executar nosso projeto seguindo as instruções abaixo.

<br>

## 🛠️ Instruções de Execução

### 1️⃣ Instalar Java 17.0

Baixe a versão do Java necessária [aqui](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### 2️⃣ Instalar Maven

Baixe e siga as instruções de instalação do Maven [aqui](https://maven.apache.org/install.html). Não se esqueça de adicioná-lo à variável `JAVA_HOME` nas variáveis de ambiente.

### 3️⃣ Instalar MySQL

#### Para Windows:
Baixe o instalador do MySQL [aqui](https://dev.mysql.com/downloads/installer/).

#### Para Linux:
```bash
sudo apt update
sudo apt install mysql-server
```
*Recomendamos configurar seu usuário com o nome de usuário "root" (padrão do MySQL) e senha "admin" para facilitar o uso do projeto. Caso opte por outras credenciais, altere-as na inicialização do pom.xml.*

### 4️⃣ Criar o Banco de Dados
Após a instalação do MySQL, execute os seguintes comandos:

#### Em Linux:
```bash
sudo mysql -u root -p
```
#### Em Windows:
```bash
mysql -u root -p
```

Digite a sua senha.
Execute os comandos abaixo para criar o banco de dados:

```bash
CREATE DATABASE InovaLab;
USE InovaLab;
SHOW TABLES;
```
Se as tabelas aparecerem, o banco de dados está funcionando como esperado!

### 5️⃣ Clonar o Repositório e Instalar Dependências
Clone o repositório e abra-o na IDE de sua preferência. Em seguida, execute:
```bash
mvn clean install
```
Esse comando instalará todas as dependências necessárias para executar o projeto.

<br>

## 🎉 O Que Fazer Após a Instalação?
Agora você pode aproveitar a integração e interação que só o InovaLab pode proporcionar para todos os membros da CESAR School. 😄

<br>

## 👩‍💻Autoria

<ul> <li> <a href="https://github.com/biapereira2">Beatriz Pereira</a> - bcbsp@cesar.school 📩 </li> <li> <a href="https://github.com/Manuelaamorim">Manuela Cavalcanti</a> - mca2@cesar.school 📩 </li> <li> <a href="https://github.com/nandaord">Maria Fernanda Ordonho</a> - mfso@cesar.school 📩 </li> <li> <a href="https://github.com/Rafabvidal">Rafaela Vidal</a> - rbv@cesar.school 📩 </li> <li> <a href="https://github.com/YgoRosa">Ygor Rosa</a> - ylr@cesar.school 📩 </li> </ul> 
