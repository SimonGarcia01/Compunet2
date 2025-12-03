import { Zap, Shield, Clock, Award } from "lucide-react";
import { motion } from "framer-motion";

export default function Stats() {
  const stats = [
    { 
      icon: Shield, 
      number: "100%", 
      label: "SEGURO",
      color: "from-[#5453E0] to-[#7B7AFF]",
      delay: 0
    },
    { 
      icon: Clock, 
      number: "24/7", 
      label: "SOPORTE",
      color: "from-[#7B7AFF] to-[#9B9AFF]",
      delay: 0.1
    },
    { 
      icon: Zap, 
      number: "∞", 
      label: "PROGRESO",
      color: "from-[#9B9AFF] to-[#5453E0]",
      delay: 0.2
    },
    { 
      icon: Award, 
      number: "PRO", 
      label: "ENTRENADORES",
      color: "from-[#5453E0] to-[#7B7AFF]",
      delay: 0.3
    }
  ];

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.2
      }
    }
  };

  const itemVariants = {
    hidden: { opacity: 0, scale: 0.5, y: 50 },
    visible: {
      opacity: 1,
      scale: 1,
      y: 0,
      transition: {
        duration: 0.6,
        ease: "easeOut"
      }
    }
  };

  return (
    <section className="relative py-32 bg-gradient-to-b from-background via-[#5453E0]/10 to-background overflow-hidden">
      {/* Background effects */}
      <div className="absolute inset-0">
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[800px] bg-[#5453E0]/10 rounded-full blur-[200px] animate-pulse-slow" />
        <div className="absolute inset-0 bg-[linear-gradient(to_right,#5453E0_0.2px,transparent_0.2px),linear-gradient(to_bottom,#5453E0_0.2px,transparent_0.2px)] bg-[size:60px_60px] opacity-5" />
      </div>
      
      <div className="relative z-10 max-w-7xl mx-auto px-6">
        <motion.div
          className="grid grid-cols-2 md:grid-cols-4 gap-8 md:gap-12"
          variants={containerVariants}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true }}
        >
          {stats.map((stat, index) => (
            <motion.div
              key={index}
              className="text-center group"
              variants={itemVariants}
              whileHover={{ scale: 1.1, y: -10 }}
            >
              <motion.div
                className="relative mb-6 flex justify-center"
                whileHover={{ rotate: [0, -10, 10, -10, 0] }}
                transition={{ duration: 0.5 }}
              >
                {/* Glow effect */}
                <motion.div
                  className={`absolute inset-0 bg-gradient-to-br ${stat.color} rounded-full blur-2xl opacity-50`}
                  animate={{
                    scale: [1, 1.2, 1],
                    opacity: [0.5, 0.8, 0.5],
                  }}
                  transition={{
                    duration: 2,
                    repeat: Infinity,
                    ease: "easeInOut",
                    delay: stat.delay
                  }}
                />
                
                {/* Icon container */}
                <div className={`relative w-20 h-20 md:w-24 md:h-24 rounded-3xl bg-gradient-to-br ${stat.color} flex items-center justify-center shadow-2xl shadow-[#5453E0]/30 group-hover:shadow-[#5453E0]/50 transition-all duration-300`}>
                  <stat.icon className="w-10 h-10 md:w-12 md:h-12 text-white" />
                </div>
              </motion.div>
              
              <motion.div
                className="font-['Montserrat'] font-black text-5xl md:text-7xl lg:text-8xl mb-4 bg-gradient-to-r from-[#5453E0] via-[#7B7AFF] to-[#5453E0] bg-clip-text text-transparent animate-gradient"
                whileHover={{ scale: 1.1 }}
              >
                {stat.number}
              </motion.div>
              
              <div className="font-['Montserrat'] font-bold text-lg md:text-xl text-muted-foreground tracking-wider mb-4">
                {stat.label}
              </div>
              
              {/* Animated underline */}
              <motion.div
                className={`mx-auto h-1 bg-gradient-to-r ${stat.color} rounded-full`}
                initial={{ width: 0 }}
                whileInView={{ width: "60%" }}
                viewport={{ once: true }}
                transition={{ duration: 0.8, delay: stat.delay }}
              />
            </motion.div>
          ))}
        </motion.div>
      </div>
    </section>
  );
}
