package org.nakedprogrammer.olympiad.controllers;

import org.nakedprogrammer.olympiad.models.Userok;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionUserHolder {
    public Map<String, Userok> container = new HashMap<>();
}
