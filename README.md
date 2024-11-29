# InovaLab

## Sobre o projeto

O InovaLab surge com o objetivo de fortalecer a conexão e engajamento entre alunos e professores na Comunidade da CESAR School, a proposta é a criação de uma aplicação integrada à plataforma online que centralize informações e traga formas práticas de inscrição para os alunos poderem participar de iniciativas e projetos. Essa solução tem como principal finalidade, através de uma extensão colocada na comunidade, o compartilhamento de informações base das iniciativas dentro da CESAR SCHOOL, mantendo os alunos e professores cientes dos projetos existentes e suas novidades, fomentando através deste tema interesse e engajamento entre os membros da comunidade, deixando-a assim mais unida e dinâmica.

# Caso queira ver melhor nosso projeto:
Você pode ver um screencast do funcionamento das principais funcionalidades [aqui](https://youtu.be/nnp3lqUalrA). Ou pode executar nosso projeto no seu computador, seguindo os passos descritos abaixo.

# Instruções antes de executar:
## 1° passo:
Instale o Java versão 17.0. Os arquivos para download do Java podem ser encontrados [neste link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html). 

## 2° passo:
Instale o Maven. Os arquivos e passos para instalação podem ser encontrados [neste link](https://maven.apache.org/install.html). Lembre-se de adicioná-lo à variável JAVA_HOME nas suas variáveis de ambiente.

## 3° passo:
Instale o MySQL. Os arquivos para instalação no windows podem ser encontrados [neste link](https://dev.mysql.com/downloads/installer/).
### Em Linux:
  `sudoapt update`

  `sudo apt install mysql-server`
  
Após instalar, recomendamos configurar seu usuário com username "root" (padrão do MySQL) e senha "admin", para que consiga acessar e rodar o projeto com mais facilidade. Caso opte por usar outras credenciais, elas precisarão ser alteradas na inicialização do pom.xml.

## 4° passo:
Para criar a base de dados digite os comandos abaixo, um após o outro, a depender do seu sistema operacional.

### Em Linux:
`sudo mysql -u root -p`

Digite a sua senha.

### Em Windows:
`mysql -u root -p`

Digite a sua senha. 

### A partir deste ponto, os comandos são iguais para ambos os sistemas operacionais.

`CREATE DATABASE InovaLab;`

`USE InovaLab;`

Neste ponto, se você digitar o comando:

`SHOW TABLES;`

Devem aparecer as tabelas utilizadas no nosso projeto, sinal de que o banco está funcionando exatamente como esperado. 

## 5° passo:

Clone o repositório e abra ele na IDE de sua preferência. Após isso, digite o comando:

`mvn clean install`

Assim você instalará todas as dependências necessárias para executar nosso projeto.

## O que fazer após realizar todos os passos de instalação?

Aproveitar a integração e interação que somente o InovaLab pode proporcionar para todos os membros da CESAR School. 😄


## 👩‍💻 Membros

<ul>
  <li>
    <a href="https://github.com/biapereira2">Beatriz Pereira</a> -
    bcbsp@cesar.school 📩
  </li>
  <li>
    <a href="https://github.com/Manuelaamorim">Manuela Cavalcanti</a> -
    mca2@cesar.school 📩
  </li>
  <li>
    <a href="https://github.com/nandaord">Maria Fernanda Ordonho</a> -
    mfso@cesar.school 📩
  </li>
  <li>
    <a href="https://github.com/Rafabvidal">Rafaela Vidal</a> -
    rbv@cesar.school 📩
  </li>
  <li>
    <a href="https://github.com/YgoRosa">Ygor Rosa</a> -
    ylr@cesar.school 📩
  </li>
</ul>

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/biapereira2">
        <img src="https://avatars3.githubusercontent.com/biapereira2" width="100px;" alt="Foto de Beatriz"/><br>
        <sub>
          <b>Beatriz C. Pereira</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Manuelaamorim">
        <img src="https://avatars.githubusercontent.com/Manuelaamorim" width="100px;" alt="Foto de Manuela"/><br>
        <sub>
          <b>Manuela C. Amorim</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/nandaord">
        <img src="https://avatars.githubusercontent.com/nandaord" width="100px;" alt="Foto de Fernanda"/><br>
        <sub>
          <b>Maria Fernanda Ordonho</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Rafabvidal">
        <img src="https://avatars.githubusercontent.com/Rafabvidal" width="100px;" alt="Foto de Rafaela"/><br>
        <sub>
          <b>Rafaela Vidal</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/YgorRosa">
        <img src="https://avatars.githubusercontent.com/YgoRosa" width="100px;" alt="Foto de Ygor"/><br>
        <sub>
          <b>Ygor Rosa</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
