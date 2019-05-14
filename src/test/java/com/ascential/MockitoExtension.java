package com.ascential;

import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Parameter;

// uma extensão deve sempre implementar Extension; é o caso da classe abaixo pois implementa TestInstancePostProcessor e ParameterResolver (que extendem Extension)
public class MockitoExtension implements TestInstancePostProcessor, ParameterResolver {

    // método da interface TestInstancePostProcessor; será invocado após a criação da instância da classe de teste
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        //inicializa os campos da classe marcados com anotações do Mockito
        MockitoAnnotations.initMocks(testInstance);
    }

    // método da interface ParameterResolver; será invocado SE o método de teste tiver algum argumento
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        // nossa extensão só se aplica para argumentos anotados com @Mock
        return parameterContext.getParameter().isAnnotationPresent(Mock.class);
    }

    // método da interface ParameterResolver; será invocado SE o método de teste tiver algum argumento e SE passar na verificação do método supportsParameter
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();

        Class<?> mockType = parameter.getType();

        // Store e Namespace são dois objetos para persistência de estado da extensão
        Store mocks = extensionContext.getStore(Namespace.create(MockitoExtension.class, mockType));

        // cria um novo mock caso ainda não exista
        return mocks.getOrComputeIfAbsent(mockType.getCanonicalName(), key -> Mockito.mock(mockType));
    }
}