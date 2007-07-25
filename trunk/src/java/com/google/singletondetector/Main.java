/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.singletondetector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.singletondetector.classpath.ClasspathRoot;
import com.google.singletondetector.classpath.DirectoryClasspathRoot;
import com.google.singletondetector.classpath.JarClasspathRoot;

/**
 * Reads through all of the classes in a given directory or jar and runs them
 * through the SingletonDetector. Prints the output to a given file.
 * 
 * @author David Rubel
 */
public class Main {
  // Version number
  private static String VERSION = "0.7.2";

  public static void main(String[] args) throws IOException {
    List<String> pargs = new ArrayList<String>();
    Flags flags = new Flags();

    // Check for flags and get args
    processArgs(args, pargs, flags);

    // Check for arg length
    if (pargs.size() != 3) {
      System.out.println("gsd.jar: invalid arguments\n");
      printUsage();
    } else {
      Main main = new Main(pargs.get(0), pargs.get(1), pargs.get(2), flags);
    }
  }

  private static void processArgs(String[] args, List<String> pargs, Flags flags) {
    // Process all args beginning with a dash and skip the rest
    for (int i = 0; i<args.length; i++) {
      String arg = args[i];
      if (arg.equals("-t")) {
        if (++i >= args.length) {
          System.out.println("gsd.jar: -t must be followed by a value\n");
          printUsage();
          System.exit(0);
        } else {
          flags.setThreshold(Integer.parseInt(args[i]));
        }
      } else if (arg.charAt(0) == '-') {
        if (!setFlags(flags, arg)) {
          System.out.println();
          printUsage();
          System.exit(0);
        }
      } else {
        pargs.add(arg);
      }
    }

    if (pargs.size() > 2) {
      // Format prefix correctly
      String prefix = pargs.get(2);
      prefix = prefix.replace(".", "/");
      if (!prefix.endsWith("/")) {
        prefix = prefix + "/";
      }
      pargs.set(2, prefix);
    } else {
      pargs.add("");
    }
  }

  private static boolean setFlags(Flags flags, String arg) {
    boolean success = true;
    for (char c : arg.toCharArray()) {
      switch (c) {
        case 'v':
          flags.setVerbose(true);
          break;
        case 'S':
          flags.setShowStats(true);
          break;
        case 'b':
          flags.setShowBanner(true);
          break;
        case 's':
          flags.setIgnoreSingletons(true);
          break;
        case 'h':
          flags.setIgnoreHingletons(true);
          break;
        case 'm':
          flags.setIgnoreMingletons(true);
          break;
        case 'f':
          flags.setIgnoreFingletons(true);
          break;
        case 'o':
          flags.setIgnoreOthers(true);
          break;
        case 'V':
          System.out.println("gsd.jar version " + VERSION);
          System.exit(0);
          break;
        case '-':
          break;
        default:
          System.out.println("gsd.jar: option -" + c + " unrecognized");
          success = false;
      }
    }
    return success;
  }

  private static void printUsage() {
    String usage =
        "Usage: java -jar gsd.jar [-(VvshmfoSb)] [-t <threshold>] <classes dir/jar> <output file> [<package>]\n"
            + " -V       - Print version and exit\n"
            + " -v       - Enable verbose mode\n"
            + " -s       - Hide singletons\n"
            + " -h       - Hide hingletons\n"
            + " -m       - Hide mingletons\n"
            + " -f       - Hide fingletons\n"
            + " -o       - Hide others\n"
            + " -S       - Print statistics upon completion\n"
            + " -b       - Add stats banner to the graph\n"
            + " -t <val> - Threshold (minimum edges required to draw a node)";

    System.out.println(usage);
  }

  public Main(String dir, String outfile, String prefix, Flags flags)
      throws IOException {
    // Get the classpath for the classes directory or jar
    URL root = new File(dir).toURI().toURL();
    ClasspathRoot classpath;
    if (dir.endsWith(".jar")) {
      classpath = new JarClasspathRoot(root);
    } else {
      classpath = new DirectoryClasspathRoot(root);
    }

    // Get the names of each class
    Set<String> classes = new HashSet<String>();
    analyzePackage(classpath, prefix, classes, flags.isVerbose());

    // Verbose: Begin processing
    if (flags.isVerbose()) {
      System.out.print("Processing... ");
    }

    // Create the singleton detector
    SingletonDetector detector =
        new SingletonDetector(classpath, prefix, flags, classes
            .toArray(new String[classes.size()]));

    // Verbose: end processing, begin output
    if (flags.isVerbose()) {
      System.out.print("done.\nGenerating output graph... ");
    }

    // Get output and write to the specified file
    BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
    out.write(detector.getGraphMlOutput());
    out.close();

    // Verbose: end output
    if (flags.isVerbose()) {
      System.out.println("done.");
    }

    // Show statistics if necessary
    if (flags.showStats()) {
      System.out.println();
      System.out.println(detector.getOutput(true));
    }
  }

  private void analyzePackage(ClasspathRoot classpath, String packageName,
      Set<String> classes, boolean verbose) throws IOException {
    for (String resource : classpath.getResources(packageName)) {
      if (resource.endsWith(".class")) {
        String className = packageName + resource;
        className = className.replace(".class", "").replace('/', '.');
        if (!className.contains("$") && !className.equals("java.lang.Enum")) {
          if (verbose) {
            System.out.println("Found: " + className);
          }
          classes.add(className);
        }
      } else {
        analyzePackage(classpath, packageName + resource + "/", classes,
            verbose);
      }
    }
  }
}