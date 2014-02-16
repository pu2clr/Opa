Introdução

Estes documento é a compilação de um estudo sobre desenvolvimento de aplicativos para dispositivos móveis com ênfase em soluções corporativas e serviços WEB. 
Como resultado de minha análise, construí um sistema em três ambiente de desenvolvimento diferentes. Quais sejam: xcode (iOS), Eclipse (Android) e Titanium (iOS, Android entre outras plataformas). Cada implementação está organizado em pastas neste repositório.

  
O sistema que desenvolvido, denominado "Opa!", simula um cenário de demandas de serviços pública onde os usuário de dispositivos móveis podem solicitar serviços de maneira ágil e precisa. Para tanto, o sistema "Opa!" faz uso dos recursos de localização, Internet e SMS disponíveis nos dispositivos móveis modernos, em especial iOS e Android.   

Além das versões implementadas para dispositivos móveis, a parte cliente da solução, há duas versões da parte Administrativa do Sistema "Opa!" que é executada em um servidor WEB. A primeira foi escrita em Java utilizando o protocolo RESTFul e JSON para comunicação com os dispositivos móveis.  A segunda foi escrita em PHP com o Framework Zend e utiliza JSON para comunicação com os dispositivos móveis.
Ambas fazem exatamente a mesma coisa e se comunicam com os mesmos aplicativos instalados nos dispositivos móveis. O objetivo aqui foi analisar robustez e facilidades técnicas existentes nesses plataformas.


A seguir tem-se mais informação sobre o sistema "Opa!. 
 
Contextualização

Os dispositivos móveis modernos como iPhone, iPad, celulares e tablets baseados em Android entre outros sistemas operacionais, possuem razoável capacidade de processamento  e excelente conectividade com o mundo exterior. Com isso, é possível executar programas complexos como navegadores WEB, acessar a maioria dos sites disponíveis na Internet e executar várias transações que até há pouco tempo só eram possíveis em computadores pessoais.  No entanto, em ambientes empresariais, utilizar aplicações desenvolvidas especificamente para Web em dispositivos móveis nem sempre é a melhor solução.  Isso porque nem todas as funções de um sistema corporativos são realizadas via WEB, seja por motivo de tecnologia (sistemas legados), por razões de segurança ou mesmo por não ser uma abordagem desejável para a empresa. 

Dispositivos móveis vem com um conjunto de componentes de interface que facilitam a utilização de programas cuja área de interação com o usuário é limitada. Em geral, este componentes não estão presentes em aplicações WEB porque, neste tipo de sistema, não há grande limitações a interface com o usuário, pois foram desenvolvidos para execução em computadores pessoais.  Além disso, a utilização dos periféricos comumente presentes nos dispositivos móveis se tornam ferramentas importantes em muitas soluções de mobilidade.  Periféricos como GPS,  Câmera Fotográfica e de Filmagem, Microfone, Acelerômetro entre outros, são instrumentos que podem ser incorporados aos aplicativos móveis gerando grandes benefícios as empresas.   Por outro lado, aplicações WEB oferecem grande poder de armazenamento e processamento, além de permitir um meio muito barato de comunicação com serviços externos. 
Então que tal juntar essas duas tecnologias?  Isto é, obter os benefícios da mobilidade com a robustez na Internet? 

Para avaliar esta demanda, foi desenvolvido um pequeno sistema que utiliza dispositivos móveis baseados nos sistemas operacionais iOS (Apple) e Android (Google).  Para representar o lado corporativo ou de serviços WEB, foi desenvolvido um sistema em PHP que executa algumas funções que serão descritas a seguir.
  

O que é o Opa! ?

OPA! é um exemplo de sistema que ilustra a integração de dispositivos móveis com sistemas de âmbito corporativo ou serviços WEB.  As informações adquiridas para construção do Opa! permitem que o profissional de tecnologia da informação conheça técnicas para integrar funções de mobilidade a sistemas corporativos.   Isto é,  possibilitar que algumas funções de um ou mais sistemas de uma organização, até mesmo sistemas legados, sejam realizadas por dispositivos móveis. 

Para tanto, o Opa! sugere ser um  sistema de utilidade pública que facilita o cidadão na aquisição ou contratação de serviços em geral.  A ideia é que a aquisição ou contratação desses serviços sejam feitas da forma mais ágil e simples possível, bastando para tanto, realizar três ações em um dispositivo móvel. Quais sejam:  executar a aplicação Opa!, selecionar o  tipo de serviço desejado e clicar em enviar.
 
O que o Opa! se propõe a fazer?

Oferecer aos usuários de dispositivos móveis facilidades para solicitação ou aquisição de serviços de utilidade pública como: Polícia, Bombeiros, SAMU, Ambulância Particular, Taxi,  Reboque, Atendimento mecânico entre outros que podem ser incorporados dinamicamente ao sistema Opa! por demanda.


Cenários:

Imagine que você esteja andando no trânsito e presencia um acidente de carro. Via o seu celular, você poderá registrar esta ocorrência e comunicar isso a Polícia, Corpo de Bombeiros ou ainda a Defesa Civil com o mínimo de esforço;  

Imagine que você está presenciando um assalto.  Com poucos cliques em seu celular você poderá registrar esta ocorrência;

Seu carro resolve dar problema justamente em um local remoto e você não sabe informar com precisão a sua localização.  Um usuário de celular avançado faria algumas operações de localização,  procuraria em alguma lista alguém ou algum serviço que pudesse atender em sua localidade. Com o aplicativo "Opa!" isso poderá ser feito em três cliques.


Você percebe que sua casa está sendo invadida e precisa acionar o serviço de segurança particular ou ainda a polícia? Com o "Opa! Você poderá fazer isso silenciosamente e em menos de 15 segundos;  

Por alguma razão você necessita de um serviço de Taxi e não tem como localizar o prestador de serviço facilmente? O Opa!!! fará isso para você com apenas três cliques. 

Você bebeu todas e está sem condições de conduzir seu veículo?  Se você ainda estiver em  condições de dar três cliques em seu celular, poderá solicitar o serviço que o ajudará achegar em casa com segurança;



Estruturas pastas (diretórios) desse Repositório

Android - Contem a implementação do aplicativo "Opa!" para o sistema operacional Android (Escrita em Java) 

Docs - Contém documentação sobre o Aplicativo Opa! além de Screenshots em iOS e Android

iOS - Contém a implementação do "Opa!" para versão iOS (Escrita em Objective C)

OpsAdmin - Contém a implementação do lado Servidor (Backend) do "Opa!" escrita em PHP

Titanium - Contém a implementação do Opa! para iOS e Android escrita em JavaScript.




