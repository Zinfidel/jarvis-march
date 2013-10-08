package com.github.zinfidel.jarvis_march;

import com.github.zinfidel.jarvis_march.geometry.*;
import com.github.zinfidel.jarvis_march.algorithm.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
// TODO: Maybe separate this into multiple statements for different packages.
@SuiteClasses({ TestConvexHull.class, TestModel.class, TestPoint.class,
	TestVector.class, TestJarvisMarcher.class })

public class TestSuite {

}