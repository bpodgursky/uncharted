### Uncharted ###

Uncharted is a work-in-progress visualization of our Sun's galactic neighborhood using [three.js](http://threejs.org/) and the [HYG star database](https://github.com/astronexus/HYG-Database).  The site is hosted here:

http://uncharted.bpodgursky.com/

![screenshot](http://i.imgur.com/1wcMYQ6.png)

### To-do ###

I have no background in astronomy (I'm just a software engineer), so while I'd like this project to be useful and accurate, I can't make any promises.  There are a number of features and improvements which I'd like to make:

- The HYG dataset is missing some newly-discovered stars (when compared against this [list](http://en.wikipedia.org/wiki/List_of_nearest_stars).  I'd like to find a fully up-to-date dataset to use.

- Current star colors and magnitudes are both inaccurate and ugly.  Right now I'm mapping stellar class to apparent color from the mapping [here](http://en.wikipedia.org/wiki/Stellar_classification), and lightening / darkening the color based on apparent magnitude.  

- I'm not happy with the current navigation controls.  The click-to-rotate feels awkward, and I'd like the ability to orbit around a fixed point.

- Ability to search for a star by name.

- List (and potentially render) known exoplanets per star.

Any suggestions / contributions / bug reports are of course extremely welcome.
