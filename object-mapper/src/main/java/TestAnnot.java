
public @interface TestAnnot {

    Class<? extends RuntimeException> values() default IllegalStateException.class;
}
