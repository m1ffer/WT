package edu.epam.fop.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Calculation {
	private double num1;
	private double num2;
	private String operation;
	private double result;
}