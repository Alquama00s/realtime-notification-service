package com.alquama00s.realtimenotificationservice.eventnotifier.rediscodecs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONCodecTest {

    static class Person {
        public String name;
        public int age;

        // default constructor for Jackson
        public Person() {}

        public Person(String name, int age) { this.name = name; this.age = age; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && (name == null ? person.name == null : name.equals(person.name));
        }
    }

    @Test
    void encodeDecodeRoundTrip() {
        JSONCodec<Person> codec = new JSONCodec<>(Person.class);

        Person p = new Person("Alice", 30);
        var bytes = codec.encodeValue(p);
        assertNotNull(bytes);

        Person decoded = codec.decodeValue(bytes);
        assertEquals(p, decoded);
    }

    @Test
    void encodeDecodeKey() {
        JSONCodec<Person> codec = new JSONCodec<>(Person.class);
        String key = "myKey";
        var bb = codec.encodeKey(key);
        assertEquals(key, codec.decodeKey(bb));
    }

    @Test
    void encodeNullValueThrows() {
        JSONCodec<Person> codec = new JSONCodec<>(Person.class);
        assertEquals(0,codec.encodeValue(null).array().length);
    }
}

