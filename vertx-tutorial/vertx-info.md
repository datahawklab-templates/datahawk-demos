### initialize
mvn io.reactiverse:vertx-maven-plugin:1.0.24:setup \
-DprojectGroupId=org.acme \
-DprojectArtifactId=acme-project \
-DprojectVersion=1.0-SNAPSHOT \ # default to 1.0-SNAPSHOT
-Dverticle=org.acme.Foo \
-Ddependencies=web

export PROJ="2-vertx" ;\
export VERTICLE="org.datahawklab.MainVerticle" ;\
export DEPEND="web" ;\
export PROJ_ROOT="$HOME/IdeaProjects/sample-code/vertx-tutorial" ;\
export PROJ_PATH="${PROJ_ROOT}/${PROJ}" ;\
echo $PROJ_PATH ;\
cd $PROJ_ROOT && mkdir $PROJ && cd $PROJ && \
mvn io.reactiverse:vertx-maven-plugin::setup -DprojectArtifactId=$PROJ -DprojectVersion=1.0-SNAPSHOT -Dverticle=$VERTICLE -Ddependencies=$DEPEND
ver

export PROJ="2-vertx" ; export VERTICLE="org.datahawklab.MainVerticle" ;export DEPEND="web" ;export PROJ_ROOT="$HOME/IdeaProjects/sample-code/vertx-tutorial" ;export PROJ_PATH="${PROJ_ROOT}/${PROJ}" ;echo $PROJ_PATH ;cd $PROJ_ROOT && mkdir $PROJ && cd $PROJ && 

mvn io.reactiverse:vertx-maven-plugin::setup -DprojectArtifactId=$PROJ -DprojectVersion=1.0-SNAPSHOT-Dverticle=$VERTICLE -Ddependencies=web