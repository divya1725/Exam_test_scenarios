#!/bin/bash
for project in */ ; do
	echo " Outside foldeer $project"
	  if [ "$project" != "ext/" ]
	  then
		  echo "run Composite Project $project"
		   publishHTML (target : [allowMissing: false,
			   alwaysLinkToLastBuild: true,
			   keepAll: true,
			   reportDir: "$project/reports",
			   reportFiles: "*.html",
			   reportName: "HTML Report-$project",
			   reportTitles: 'The Report1']
			  )
	   fi
done