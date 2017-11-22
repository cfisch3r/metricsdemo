# Aspektorientierung

Für die Integration querschnittlicher Leistungsmerkmale bietet sich ein Aspektorientierter Ansatz um, um diese Funktionalität in zentralen Komponenten zu implementieren. Da es sich dabei um Infrastrukturbausteine handelt, werden diese in der Adapterschicht der Hexagonalen Architektur angesiedelt. Bei der Wahl der Strategie zur Integration mit der Kernapplikation stehen Wartbarkeit und Erweiterbarkeit der Lösung im Vordergrund.


## Varianten zur Definition der Pointcuts
Fur die Integrationspunkte (Pointcuts) der querschnittlichen Funktionalität kommen zwei Varianten in Betracht:

- *Execution-Pointcuts* definieren ein Namensschema für alle Methoden, die mit der gewünschten Funktion erweitert werden soll.

Pro:
- zentrale Sicht auf alle Methoden eines Aspektes

Kontra:
- Mögliche Probleme beim Refactoring (nur durch IDE-Features sicherzustellen)

- *Annotations-Pointcuts* definieren den Integrationspunkt über eine Annotation, die zu den Zielmethoden eines Aspekts hinzugefügt wird.

Pro:
- Gute Sichtbarkeit, welche Aspekte an einer Methode wirksam sind.

Kontra:
- Nichtfunktionale Konzepte werden im Application Core sichtbar

## Varianten zur Proxygenerierung
Die Integration von Aspekt- und Methodenaufruf erfolgt mit Hilfe von [Dynamic Proxies](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html). Zwei Wege sind hierbei möglich.

### Spring Aspects
Bei der Erzeugung von Spring Beans werden diese durch das Framework in Proxy-Objekte integriert. Aspekte (Advices), die über eine @Aspect-Annotation verfügen, werden automatisch hinzugefügt.

Pro:
- Standardverfahren im Spring-Framework ohne zusätzliche Implementierung
- Keine Anpassung der Kernapplikation notwendig

Kontra:
- Aspekte können nur Objekten hinzugefügt werden, die von Spring als Bean erzeugt werden.

### Proxy-Decorator-Interface
In der Kernapplikation wird ein Interface definiert, über das Objektinstanzen mit einem Proxy versehen werden können, der die in der Adapterschicht definierten Advices aufruft.

```java

public interface ProxyDecoratorService {

    <T,A extends Annotation> T addProxyWithAspects(T subject, Class<A> annotationClass);
}

public class Demo {

    static class InternalObject {

    }

    public Demo(ProxyDecoratorService decoratorService) {
        InternalObject objectWithProxy = decoratorService.addProxyWithAspects(new InternalObject(), MarkingAnnotation.class);
    }
}

```

In der Adapterschicht wird dann dieses Interface unter Verwendung der *ProxyFactory* implementiert.

```java

public class SpringProxyDecoratorService implements ProxyDecoratorService {

    @Override
    public <T, A extends Annotation> T addMetrics(T subject, Class<A> annotationClass) {
        ProxyFactory factory = new ProxyFactory(subject);
        factory.addAdvice(new Advice(annotationClass));
        return (T) factory.getProxy();
    }
}

```

Pro:
- Kann für Objekte verwendet werden, die innerhalb der Kernapplikation erzeugt werden.

Kontra:
- Methode des Decorator-Service-Interfaces muss explizit aufgerufen werden.
- Eigene Implemmentierung der Proxygenerierung.

## Beispiele

Proxy-Decorator-Interface siehe Paket *de.agiledojo.metricsdemo.app.metrics.proxy*.

Spring Aspect mit Annotation-Pointcut siehe Paket *de.agiledojo.metricsdemo.app.metrics.aspect*.
