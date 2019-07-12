# jenkins with docker-compose

### jenkins version update


    ```sh
    // root connect
    docker exec -it -u 0 "container name" /bin/bash
    
    //file mv
    mv ${jenkins_home}/jenkins.war /usr/share/jenkins
    
    //priviliges
    chown jenkins:jenkins /usr/share/jenkins/jenkins.war
    
    docker restart "container name"
    
    ```