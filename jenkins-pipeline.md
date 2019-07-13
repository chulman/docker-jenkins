
# jenkins-pipeline

- pipeline is parallel jobs
- pipeline supports Declarative and scripts.
    + Scripted Pipeline, like Declarative Pipeline, is built on top of the underlying Pipeline sub-system. Unlike Declarative, Scripted Pipeline is effectively a general purpose DSL built with Groovy. 

- pipeline scripts 2가지 방법으로 작성 가능
    1. web 
    2. git
    



## scripts pipeline    
    
| Directive | 설명 |
| ------------------ | --------------- |
| node | Scripted 파이프라인을 실행하는 젠킨스 에이전트, 최상단 선언 |
| dir  | 명령을 수행할 디렉토리 / 폴더 정의 |
| stage |  파이프라인의 각 step, 어떤 작업을 실행할지 선언 |
| git | git clone |
| sh | sh 명령어 |
| def | function or variable |


- jenkins pipeline syntax 에서 클릭 설정을 통해 스크립트를 자동 생성이 가능하다.

## node

![node](https://t1.daumcdn.net/cfile/tistory/99DCBB375C00E7CC24)


## exception flow

```groovy
node {
    stage('Example') {
        try {
            sh 'exit 1'
        }
        catch (exc) {
            echo 'exception occured.' + exc
            throw exception();
        }
    }
}
```

## ref
- https://jenkins.io/doc/book/pipeline/getting-started/
- https://jenkins.io/doc/book/pipeline/syntax/
- https://jojoldu.tistory.com/356?category=777282