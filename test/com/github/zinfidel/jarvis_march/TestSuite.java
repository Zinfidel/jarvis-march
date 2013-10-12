package com.github.zinfidel.jarvis_march;

import com.github.zinfidel.jarvis_march.geometry.*;
import com.github.zinfidel.jarvis_march.algorithm.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestConvexHull.class, TestModel.class, TestPoint.class,
	TestVector.class, TestJarvisMarcher.class })

public class TestSuite {

}