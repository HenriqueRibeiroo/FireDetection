# Simulação de Sistema de Detecção de Incêndios com Comunicação entre Sensores

## Objetivo
Este projeto visa implementar um sistema de simulação para detecção de incêndios utilizando programação paralela e distribuída com `pthreads` e monitores para comunicação e sincronização entre threads. A simulação envolve a criação de funções que utilizam monitores para permitir a comunicação segura entre diferentes threads que simulam nós sensores em uma matriz. Também é necessário implementar uma thread que gera incêndios aleatórios periodicamente.

## Descrição
O sistema simula o monitoramento de incêndios em uma floresta representada por uma matriz de 30x30 células, onde cada célula é monitorada por um nó sensor. Os sensores comunicam entre si para detectar e propagar informações sobre incêndios. Em casos onde o nó está localizado na borda da matriz, ele envia mensagens para uma thread central responsável por registrar os eventos de incêndio e iniciar o combate ao fogo. Além disso, uma thread geradora de incêndios cria incêndios aleatórios em intervalos regulares.

### Estados das Células na Floresta
- `-`: Área livre.
- `T`: Célula monitorada por um nó sensor ativo.
- `@`: Célula em chamas (fogo ativo).
- `/`: Célula queimada.

## Especificações do Sistema
1. **Tamanho da Floresta**: Uma matriz de 30x30 que representa a área monitorada.
2. **Nó Sensor**:
   - Cada nó é representado por uma thread independente.
   - As threads comunicam-se com seus vizinhos usando monitores para garantir exclusão mútua e comunicação segura (utilizando `mutexes` e variáveis de condição).
3. **Thread Geradora de Incêndios**:
   - Gera incêndios aleatórios na matriz a cada intervalo de tempo (por exemplo, 3 segundos).
   - Marca a célula selecionada como fogo `@`.
4. **Central de Controle**:
   - Coleta mensagens dos nós localizados na borda da matriz.
   - Atua para combater incêndios quando necessário.
5. **Combate ao Fogo**:
   - Função que altera o estado das células em chamas `@` para células queimadas `/`.

## Requisitos Técnicos
- Utilizar `pthreads` para a criação das threads (sensores, central e geradora de incêndios).
- Implementar monitores para comunicação e sincronização das threads.
- Garantir exclusão mútua com `mutexes` e controlar o fluxo de mensagens entre os nós com variáveis de condição.
- Seguir boas práticas de programação e modularidade no código.
