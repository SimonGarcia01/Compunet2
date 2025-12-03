import { GraduationCap, Users, Trophy, Target, TrendingUp } from "lucide-react";
import { motion } from "framer-motion";

export default function StudentsBanner() {
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
    hidden: { opacity: 0, x: -50 },
    visible: {
      opacity: 1,
      x: 0,
      transition: {
        duration: 0.8,
        ease: "easeOut"
      }
    }
  };

  const rightVariants = {
    hidden: { opacity: 0, x: 50 },
    visible: {
      opacity: 1,
      x: 0,
      transition: {
        duration: 0.8,
        ease: "easeOut"
      }
    }
  };

  return (
    <section className="relative py-32 bg-gradient-to-br from-foreground via-[#5453E0] to-foreground text-background overflow-hidden">
      {/* Background pattern with purple */}
      <div className="absolute inset-0 opacity-20">
        <div className="absolute inset-0 bg-[linear-gradient(to_right,#ffffff15_1px,transparent_1px),linear-gradient(to_bottom,#ffffff15_1px,transparent_1px)] bg-[size:40px_40px]" />
      </div>
      
      {/* Floating gradient orbs */}
      <motion.div
        className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[800px] h-[800px] bg-background/10 rounded-full blur-[200px]"
        animate={{
          scale: [1, 1.3, 1],
          rotate: [0, 180, 360],
        }}
        transition={{
          duration: 20,
          repeat: Infinity,
          ease: "linear"
        }}
      />
      
      <div className="relative z-10 max-w-7xl mx-auto px-6">
        <motion.div
          className="grid md:grid-cols-2 gap-16 items-center"
          variants={containerVariants}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: true }}
        >
          <motion.div className="space-y-8" variants={itemVariants}>
            <motion.div
              className="inline-flex items-center gap-2 px-6 py-3 rounded-full border-2 border-background/30 bg-background/10 backdrop-blur-md"
              whileHover={{ scale: 1.05 }}
            >
              <GraduationCap className="w-6 h-6 text-background" />
              <span className="font-['Montserrat'] font-bold text-sm tracking-wide">Diseñado para Estudiantes</span>
            </motion.div>
            
            <h2 className="font-['Montserrat'] font-black text-5xl md:text-6xl lg:text-7xl leading-tight">
              UNA COMUNIDAD
              <br />
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-background via-white to-background animate-gradient">
                DE CAMPEONES
              </span>
            </h2>
            
            <p className="font-['Montserrat'] text-xl md:text-2xl opacity-95 font-semibold leading-relaxed">
              Únete a miles de estudiantes que están transformando su salud y rendimiento académico 
              a través del fitness. Tu universidad, tu gimnasio, tu comunidad.
            </p>
            
            <div className="grid grid-cols-2 gap-6 pt-6">
              <motion.div
                className="space-y-3 p-6 rounded-2xl bg-background/10 backdrop-blur-sm border border-background/20"
                whileHover={{ scale: 1.05, backgroundColor: "rgba(255, 255, 255, 0.15)" }}
              >
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-[#5453E0] to-[#7B7AFF] flex items-center justify-center">
                    <Users className="w-6 h-6 text-white" />
                  </div>
                  <span className="font-['Montserrat'] font-black text-3xl">2,500+</span>
                </div>
                <p className="font-['Montserrat'] text-sm opacity-90 font-semibold">Estudiantes Activos</p>
              </motion.div>
              
              <motion.div
                className="space-y-3 p-6 rounded-2xl bg-background/10 backdrop-blur-sm border border-background/20"
                whileHover={{ scale: 1.05, backgroundColor: "rgba(255, 255, 255, 0.15)" }}
              >
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 rounded-xl bg-gradient-to-br from-[#7B7AFF] to-[#9B9AFF] flex items-center justify-center">
                    <Trophy className="w-6 h-6 text-white" />
                  </div>
                  <span className="font-['Montserrat'] font-black text-3xl">150+</span>
                </div>
                <p className="font-['Montserrat'] text-sm opacity-90 font-semibold">Eventos Mensuales</p>
              </motion.div>
            </div>
          </motion.div>
          
          <motion.div className="relative" variants={rightVariants}>
            <motion.div
              className="relative w-full aspect-square max-w-lg mx-auto"
              whileHover={{ scale: 1.05 }}
            >
              {/* Glowing logo container */}
              <motion.div
                className="absolute inset-0 bg-background/20 backdrop-blur-2xl rounded-3xl border-2 border-background/30 shadow-2xl"
                animate={{
                  boxShadow: [
                    "0 0 40px rgba(255, 255, 255, 0.1)",
                    "0 0 80px rgba(84, 83, 224, 0.3)",
                    "0 0 40px rgba(255, 255, 255, 0.1)",
                  ],
                }}
                transition={{
                  duration: 3,
                  repeat: Infinity,
                  ease: "easeInOut"
                }}
              />
              
              <div className="absolute inset-4 flex items-center justify-center">
                <motion.img
                  src="/images/students-logo.png"
                  alt="Plataforma Estudiantil"
                  className="w-full h-full object-contain"
                  animate={{
                    y: [0, -10, 0],
                  }}
                  transition={{
                    duration: 3,
                    repeat: Infinity,
                    ease: "easeInOut"
                  }}
                />
              </div>
              
              {/* Floating badges */}
              <motion.div
                className="absolute -top-8 -right-8 bg-gradient-to-br from-[#5453E0] to-[#7B7AFF] text-white px-6 py-4 rounded-2xl shadow-2xl border-2 border-background/20"
                animate={{
                  y: [0, -10, 0],
                  rotate: [0, 5, -5, 0],
                }}
                transition={{
                  duration: 3,
                  repeat: Infinity,
                  ease: "easeInOut"
                }}
              >
                <div className="flex items-center gap-2">
                  <Target className="w-5 h-5" />
                  <span className="font-['Montserrat'] font-black">100% Estudiantes</span>
                </div>
              </motion.div>
              
              <motion.div
                className="absolute -bottom-8 -left-8 bg-gradient-to-br from-[#7B7AFF] to-[#9B9AFF] text-white px-6 py-4 rounded-2xl shadow-2xl border-2 border-background/20"
                animate={{
                  y: [0, 10, 0],
                  rotate: [0, -5, 5, 0],
                }}
                transition={{
                  duration: 3,
                  repeat: Infinity,
                  ease: "easeInOut",
                  delay: 0.5
                }}
              >
                <div className="flex items-center gap-2">
                  <GraduationCap className="w-5 h-5" />
                  <span className="font-['Montserrat'] font-black">Acceso Institucional</span>
                </div>
              </motion.div>
            </motion.div>
          </motion.div>
        </motion.div>
      </div>
    </section>
  );
}
