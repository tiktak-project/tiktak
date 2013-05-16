para usar o TikTak em seu projeto Maven

1. Adicionar o repositorio Maven do Github da seguinte forma:
```
       <repository>
	  <id>my.mvn.repo</id>
	  <url>https://raw.github.com/tiktak-project/tiktak/mvn-repo/</url>
	  <!-- use snapshot version -->
	  <snapshots>
	     <enabled>true</enabled>
	     <updatePolicy>always</updatePolicy>
	  </snapshots>
	</repository>
```

2. Adicionar a dependencia da versão a ser usada da seguinte forma:
```
    	<dependency>
	  <groupId>br.org.tiktak</groupId>
	  <artifactId>tiktak-api</artifactId>
	  <version>0.2</version>
        </dependency>
```

Licença: LGPL - http://www.gnu.org/copyleft/lesser.html
