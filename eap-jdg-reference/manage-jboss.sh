start(){
        PID=`ps -ef | grep jboss | grep "=$NODE_NAME " | awk '{print $2}'`
        if [ e$PID != "e" ]
                then
                echo "###############################################"
                echo "JBOSS PID = $NODE_NAME $PID                    "
                echo "JBoss SERVER - $NODE_NAME is already RUNNING..."
                echo "###############################################"
        exit;
        fi
        echo "#######################"
        echo "   Starting JBoss EAP  "
        echo "#######################"
             nohup $JBOSS_HOME/bin/standalone.sh --server-config=standalone-ha.xml -Djboss.server.base.dir=$SERVER_HOME -bmanagement $SERVER_IP -b $SERVER_IP 1> /dev/null 2>&1 &

        sleep 5
        _up=`netstat -an | grep $CONTROLLER_PORT | grep -v grep | wc -l`
              if [[ "${_up}" != "0" ]]; then
                 echo "###############################################"
                 echo "JBoss Server is Up!! $NODE_NAME  And Running.!!"
                 echo "###############################################"
              else
                 echo "###############################################"
                 echo "JBoss Server is Down!! $NODE_NAME              "
                 echo "###############################################"
              fi;
}

stop(){
        echo "#######################"
        echo "    Stopping JBoss     "
        echo -e " password : \c "
        read PASSWORD
        echo "#######################"
        $JBOSS_HOME/bin/jboss-cli.sh -c --controller=$SERVER_IP:$CONTROLLER_PORT --connect command=:shutdown --user=$USER --password=$PASSWORD

#        $JBOSS_HOME/bin/jboss-cli.sh -c --controller=$SERVER_IP:$CONTROLLER_PORT --connect command=:shutdown --user=$USER --password=$PASSWD
}

status() {
                echo Checking JBoss Status..
                echo Wait for a while...
                        _up=`netstat -an | grep $CONTROLLER_PORT | grep -v grep | wc -l`
                        if [[ "${_up}" != "0" ]]; then
                                echo "###############################################"
                                echo "JBoss Server is Up!! $NODE_NAME  And Running.!!"
                                echo "###############################################"
                        else
                                echo "##################################"
                                echo "JBoss Server is Down!! $NODE_NAME "
                                echo "##################################"
                        fi;
}

case "$1" in
  start)
        start
        ;;
  stop)
        stop
        ;;
  status)
        status
        ;;
  *)
        echo "Usage: jboss {start|stop|status}"
        exit 1
esac
exit 0