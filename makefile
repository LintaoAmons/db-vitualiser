generate: generate_jooq_tables
	mvn clean install
	java \
    -Dfile.encoding=UTF-8 \
    -classpath ${HOME}/Documents/projects/db-visualiser/target/classes:${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-reflect/1.6.21/kotlin-reflect-1.6.21.jar:${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/1.6.21/kotlin-stdlib-1.6.21.jar:${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.6.21/kotlin-stdlib-common-1.6.21.jar:${HOME}/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.6.21/kotlin-stdlib-jdk8-1.6.21.jar:${HOME}/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.6.21/kotlin-stdlib-jdk7-1.6.21.jar:${HOME}/.m2/repository/org/jooq/jooq/3.16.8/jooq-3.16.8.jar:${HOME}/.m2/repository/io/r2dbc/r2dbc-spi/0.9.0.RELEASE/r2dbc-spi-0.9.0.RELEASE.jar:${HOME}/.m2/repository/org/reactivestreams/reactive-streams/1.0.3/reactive-streams-1.0.3.jar:${HOME}/.m2/repository/jakarta/xml/bind/jakarta.xml.bind-api/3.0.0/jakarta.xml.bind-api-3.0.0.jar:${HOME}/.m2/repository/com/sun/activation/jakarta.activation/2.0.0/jakarta.activation-2.0.0.jar:${HOME}/.m2/repository/org/postgresql/postgresql/42.3.8/postgresql-42.3.8.jar:${HOME}/.m2/repository/org/checkerframework/checker-qual/3.5.0/checker-qual-3.5.0.jar:${HOME}/.m2/repository/javax/json/javax.json-api/1.1.4/javax.json-api-1.1.4.jar:${HOME}/.m2/repository/org/glassfish/javax.json/1.1.4/javax.json-1.1.4.jar \
    top.oatnil.db_visualiser.MainKt

generate_jooq_tables:
	echo "\n\033[33mNote: jooq require java 17 to run\033[m\n"
	mvn jooq-codegen:generate
