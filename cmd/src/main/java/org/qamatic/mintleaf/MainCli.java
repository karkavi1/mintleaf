/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */

package org.qamatic.mintleaf;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

/**
 * Created by qamatic on 2/18/6/16.
 */

public class MainCli {

    @Parameter(names = {"-h", "--help"}, help = true)
    private String help;


    @Parameter(names = {"-v", "--version"}, help = true)
    private String version;

    private JCommander jc = new JCommander(this);
    private CommandMigrate cc = new CommandMigrate();


    public MainCli parse(String[] args) {
        jc.setProgramName("Mintleaf");
        jc.addCommand("migrate", cc);
        jc.setCaseSensitiveOptions(false);
        jc.setAllowAbbreviatedOptions(true);
        try {
            if (args.length == 0) {
                throw new RuntimeException();
            }
            jc.parse(args);
        } catch (RuntimeException e) {
            usage();
        }
        return this;
    }

    public void usage() {
        StringBuilder sb = new StringBuilder();
        jc.usage(sb);
        JCommander.getConsole().println(sb.toString().replaceAll("\n\n", "\n"));
    }

    public void run() {

    }

    public static void main(String[] args) {
        System.out.println("Mintleaf v1.23 command line tool");
        new MainCli().parse(args).run();
    }

    @Parameters(separators = "=", commandDescription = "")
    class CommonOptions {
        @Parameter(names = "-config", required = true, description = "Database settings and Schema version configuration file")
        private String configFile;
    }

    @Parameters(separators = "=", commandDescription = "Migrate schema")
    private class CommandMigrate {

        @ParametersDelegate
        private CommonOptions commonOptions = new CommonOptions();

    }
}

//
//    CommandLineParser parser = new DefaultParser();
//
//    Options options = new Options();
//options.addOption("help", "help", false, "usage");
//
//
//        try {
//        // parse the command line arguments
//        CommandLine line = parser.parse(options, args);
//        if (args.length == 0) {
//        HelpFormatter formatter = new HelpFormatter();
//        formatter.printHelp("mintleaf", options);
//        System.exit(0);
//        }
//
//        } catch (ParseException exp) {
//        System.out.println("Unexpected exception:" + exp.getMessage());
//        System.exit(-1);
//        }